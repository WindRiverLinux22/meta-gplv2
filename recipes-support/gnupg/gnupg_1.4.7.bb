SUMMARY = "GNU Privacy Guard - encryption and signing tools"
HOMEPAGE = "http://www.gnupg.org/"
DEPENDS = "zlib bzip2 readline"
SECTION = "console/utils"

LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

PR = "r9"

SRC_URI = "${GNUPG_MIRROR}/gnupg/gnupg-${PV}.tar.bz2 \
           file://long-long-thumb.patch \
           file://configure.patch \
           file://mips_gcc4.4.patch \
           file://GnuPG1-CVE-2012-6085.patch \
           file://curl_typeof_fix_backport.patch \
           file://CVE-2013-4351.patch \
           file://CVE-2013-4576.patch \
           file://CVE-2013-4242.patch \
           file://fix-ustar-check-issue.patch \
           file://0001-Make-it-build-with-gettext-0.20.patch \
           "

SRC_URI[md5sum] = "b06a141cca5cd1a55bbdd25ab833303c"
SRC_URI[sha256sum] = "69d18b7d193f62ca27ed4febcb4c9044aa0c95305d3258fe902e2fae5fc6468d"

inherit autotools gettext texinfo

#   --with-egd-socket=NAME  use NAME for the EGD socket
#   --with-photo-viewer=FIXED_VIEWER  set a fixed photo ID viewer
#   --with-included-zlib    use the zlib code included here
#   --with-capabilities     use linux capabilities default=no
#   --with-mailprog=NAME    use "NAME -t" for mail transport
#   --with-libiconv-prefix[=DIR]  search for libiconv in DIR/include and DIR/lib
#   --without-libiconv-prefix     don't search for libiconv in includedir and libdir
#   --with-included-gettext use the GNU gettext library included here
#   --with-libintl-prefix[=DIR]  search for libintl in DIR/include and DIR/lib
#   --without-libintl-prefix     don't search for libintl in includedir and libdir
#   --without-readline      do not support fancy command line editing
#   --with-included-regex   use the included GNU regex library
#   --with-zlib=DIR         use libz in DIR
#   --with-bzip2=DIR        look for bzip2 in DIR
#   --enable-static-rnd=egd|unix|linux|auto
#   --disable-dev-random    disable the use of dev random
#   --disable-asm           do not use assembler modules
#   --enable-m-guard        enable memory guard facility
#   --enable-selinux-support
#                           enable SELinux support
#   --disable-card-support  disable OpenPGP card support
#   --disable-gnupg-iconv   disable the new iconv code
#   --enable-backsigs       enable the experimental backsigs code
#   --enable-minimal        build the smallest gpg binary possible
#   --disable-rsa           disable the RSA public key algorithm
#   --disable-idea          disable the IDEA cipher
#   --disable-cast5         disable the CAST5 cipher
#   --disable-blowfish      disable the BLOWFISH cipher
#   --disable-aes           disable the AES, AES192, and AES256 ciphers
#   --disable-twofish       disable the TWOFISH cipher
#   --disable-sha256        disable the SHA-256 digest
#   --disable-sha512        disable the SHA-384 and SHA-512 digests
#   --disable-bzip2         disable the BZIP2 compression algorithm
#   --disable-exec          disable all external program execution
#   --disable-photo-viewers disable photo ID viewers
#   --disable-keyserver-helpers  disable all external keyserver support
#   --disable-ldap          disable LDAP keyserver interface
#   --disable-hkp           disable HKP keyserver interface
#   --disable-http          disable HTTP key fetching interface
#   --disable-finger        disable Finger key fetching interface
#   --disable-mailto        disable email keyserver interface
#   --disable-keyserver-path disable the exec-path option for keyserver helpers
#   --enable-key-cache=SIZE Set key cache to SIZE (default 4096)
#   --disable-largefile     omit support for large files
#   --disable-dns-srv       disable the use of DNS SRV in HKP and HTTP
#   --disable-nls           do not use Native Language Support
#   --disable-regex         do not handle regular expressions in trust sigs

EXTRA_OECONF = "--disable-ldap \
                --with-zlib=${STAGING_LIBDIR}/.. \
                --with-bzip2=${STAGING_LIBDIR}/.. \
                --disable-selinux-support \
                --with-readline=${STAGING_LIBDIR}/.. \
                ac_cv_sys_symbol_underscore=no \
                ac_cv_path_TAR=${base_bindir}/tar \
               "

# Force gcc's traditional handling of inline to avoid issues with gcc 5
BUILD_CFLAGS += "-fgnu89-inline"
CFLAGS += "-fgnu89-inline"

# Force -fcommon to avoid issues with GCC 10 (which defaults to -fno-common)
BUILD_CFLAGS += "-fcommon"
CFLAGS += "-fcommon"

do_install () {
	autotools_do_install
	install -d ${D}${docdir}/${BPN}
	mv ${D}${datadir}/${BPN}/* ${D}/${docdir}/${BPN}/ || :
	mv ${D}${prefix}/doc/* ${D}/${docdir}/${BPN}/ || :
}

# split out gpgv from main package
RDEPENDS:${PN} = "gpgv"
RDEPENDS:${PN}:class-native = ""

PACKAGES =+ "gpgv"
FILES:gpgv = "${bindir}/gpgv"

# Exclude debug files from the main packages
FILES:${PN} = "${bindir}/* ${datadir}/${BPN} ${libexecdir}/${BPN}/*"

PACKAGECONFIG ??= ""
PACKAGECONFIG[curl] = "--with-libcurl=${STAGING_LIBDIR},--without-libcurl,curl"
PACKAGECONFIG[libusb] = "--with-libusb=${STAGING_LIBDIR},--without-libusb,libusb-compat"

BBCLASSEXTEND += "native"
