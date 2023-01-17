SRC_URI:append = " \
	file://0001-Commented-I2C-mux-code-for-Digilent-Genesys-ZU.patch \
	file://0002-fsbl-Reset-usb-phys-and-hub-upon-board-init.patch \
	file://0003-zynqmp_dram_test-Added-board-specific-reference-freq.patch \
	file://0004-Added-Genesys-ZU-to-list-of-boards-supporting-DDR4-d.patch \
	file://0005-zynqmp_fsbl-default-to-debug-level-prints.patch \
	"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
  
#Add debug for FSBL(optional)
XSCTH_BUILD_DEBUG = "1"
  
# Enable UHS-I speeds for SD
YAML_COMPILER_FLAGS:append = " -DUHS_MODE_ENABLE"
YAML_COMPILER_FLAGS:append = " -DXPS_BOARD_GZU_5EV"

# Workaround erroneous -O2 compilation setting in FSBL
do_configure:append() {
    sed -i "/BSP_FLAGS := -O2 -c/d" ${WORKDIR}/git/fsbl-firmware/fsbl-firmware/Makefile
}
