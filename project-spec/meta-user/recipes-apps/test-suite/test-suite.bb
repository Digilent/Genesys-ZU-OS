#
# This file is the zuca-test-suite recipe.
#

SUMMARY = "Simple zuca-test-suite application"
SECTION = "PETALINUX/apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://test-suite \
	file://rtc-test \
	file://uio-test \
	file://sysmon \
	file://sim-bist \
	file://usb-bist \
	file://usb-c-reset \
	file://DP-bist \
	file://DP-bist-wrapper \
	file://network-bist \
	file://wifi-bist \
	file://pci-bist \
	file://test-suite.service \
	file://find-i2c-bus \
	file://type-c-dir \
	file://sfp-gpio-bist \
	"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "test-suite.service"


do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/test-suite ${D}/${bindir}
	install -m 0755 ${S}/rtc-test ${D}/${bindir}
	install -m 0755 ${S}/uio-test ${D}/${bindir}
	install -m 0755 ${S}/sysmon ${D}/${bindir}
	install -m 0755 ${S}/sim-bist ${D}/${bindir}
	install -m 0755 ${S}/usb-bist ${D}/${bindir}
	install -m 0755 ${S}/usb-c-reset ${D}/${bindir}
	install -m 0755 ${S}/DP-bist ${D}/${bindir}
	install -m 0755 ${S}/DP-bist-wrapper ${D}/${bindir}
	install -m 0755 ${S}/network-bist ${D}/${bindir}
	install -m 0755 ${S}/wifi-bist ${D}/${bindir}
	install -m 0755 ${S}/pci-bist ${D}/${bindir}
	install -m 0755 ${S}/find-i2c-bus ${D}/${bindir}
	install -m 0755 ${S}/type-c-dir ${D}/${bindir}
	install -m 0755 ${S}/sfp-gpio-bist ${D}/${bindir}

	#systemd
	install -d ${D}/${systemd_system_unitdir}
	install -m 0644 ${S}/test-suite.service ${D}/${systemd_system_unitdir}
}

FILES_${PN} += "${systemd_unitdir}/*"
