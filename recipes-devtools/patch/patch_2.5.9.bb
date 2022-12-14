require patch.inc
LICENSE = "GPL-2.0-only"

SRC_URI += " file://debian.patch \
	   file://install.patch \
           file://unified-reject-files.diff \
           file://global-reject-file.diff \
           file://0001-Make-it-build-with-compile-time-hardening-enabled.patch"
PR = "r3"

LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SRC_URI[md5sum] = "dacfb618082f8d3a2194601193cf8716"
SRC_URI[sha256sum] = "ecb5c6469d732bcf01d6ec1afe9e64f1668caba5bfdb103c28d7f537ba3cdb8a"
