#!/usr/bin/env python3
"""Render iOS app icons from the existing high-resolution SVG design.

The Muse-provided icon ZIP was validated as a sizing reference. Instead of
upscaling the 512px raster, render from the matching vector source for quality.
This script needs CairoSVG and Pillow, installed in the CI workflow.
"""
import io
import json
from pathlib import Path

import cairosvg
from PIL import Image

repo = Path(__file__).resolve().parents[1]
source = (repo / "icon.svg").read_text(encoding="utf-8")
# iOS applies its own rounded corner mask; fill the source canvas to all edges.
source = source.replace('width="512" height="512" rx="110"', 'width="512" height="512" rx="0"')
asset_dir = repo / "ios/PulseDrift/Assets.xcassets/AppIcon.appiconset"
spec = json.loads((asset_dir / "Contents.json").read_text(encoding="utf-8"))
size_set = sorted({int(img["filename"][5:-4]) for img in spec["images"]})
rendered = cairosvg.svg2png(bytestring=source.encode("utf-8"), output_width=1024, output_height=1024)
master = Image.open(io.BytesIO(rendered)).convert("RGB")

for size in size_set:
    image = master if size == 1024 else master.resize((size, size), Image.Resampling.LANCZOS)
    dest = asset_dir / f"icon-{size}.png"
    image.save(dest, "PNG", optimize=True)
    check = Image.open(dest)
    assert check.size == (size, size) and check.mode == "RGB", dest
    print(f"Created {dest.relative_to(repo)} ({size}x{size}, RGB/no alpha)")
assert all((asset_dir / image["filename"]).is_file() for image in spec["images"])
print("All iOS icons generated and validated.")
