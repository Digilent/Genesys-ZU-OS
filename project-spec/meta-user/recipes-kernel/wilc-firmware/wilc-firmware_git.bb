HOMEPAGE = "https://github.com/linux4wilc/firmware"

LICENSE = "WILC-Firmware"
NO_GENERIC_LICENSE[WILC-Firmware] = "LICENSE.wilc_fw"

LIC_FILES_CHKSUM = "file://LICENSE.wilc_fw;md5=89ed0ff0e98ce1c58747e9a39183cc9f"
SRC_URI = "git://github.com/linux4wilc/firmware.git;branch=master;protocol=https"
SRCREV = "7f5bc9b7ea7ddea22edd6c9b9117008f1a655d8c"
PE = "1"
PV = "0.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit allarch

CLEANBROKEN = "1"

do_compile() {
    :
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/mchp
    cp wilc*.bin ${D}${nonarch_base_libdir}/firmware/mchp

    # For license package:
    install -m 0644 LICENSE.wilc_fw ${D}${nonarch_base_libdir}/firmware/mchp/
}

PACKAGES =+ " \
    ${PN}-1000-wifi \
    ${PN}-3000-wifi \
    ${PN}-3000-ble \
    ${PN}-license \
"

RDEPENDS:${PN}-1000-wifi = "${PN}-license"
RDEPENDS:${PN}-3000-wifi = "${PN}-license"
RDEPENDS:${PN}-3000-ble = "${PN}-license"

FILES:${PN}-1000-wifi = "${nonarch_base_libdir}/firmware/mchp/wilc1000_wifi_firmware.bin"
FILES:${PN}-3000-wifi = "${nonarch_base_libdir}/firmware/mchp/wilc3000_wifi_firmware.bin"
FILES:${PN}-3000-ble = "${nonarch_base_libdir}/firmware/mchp/wilc3000_ble*.bin"
FILES:${PN}-license += "${nonarch_base_libdir}/firmware/mchp/LICENSE.wilc_fw"

FILES:${PN} += "${nonarch_base_libdir}/firmware/mchp/*"
RDEPENDS:${PN} += "${PN}-license"

# Make wilc-firmware depend on all of the split-out packages.
python populate_packages:prepend () {
    firmware_pkgs = oe.utils.packages_filter_out_system(d)
    d.appendVar('RDEPENDS:wilc-firmware', ' ' + ' '.join(firmware_pkgs))
}
