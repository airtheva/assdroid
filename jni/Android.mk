LOCAL_PATH := $(call my-dir)

LIBASSIMP_INCLUDE_PATH := ../prebuilts/assimp
LIBPNG_INCLUDE_PATH := ../prebuilts/libpng16
LIBDEVIL_SRC_PATH := devil-1.7.8

include $(CLEAR_VARS)

LOCAL_MODULE    := libassimp
LOCAL_SRC_FILES := ../prebuilts/libassimp.3.0.1264.so
LOCAL_MODULE_FILENAME := libassimp

include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := libpng
LOCAL_SRC_FILES := ../prebuilts/libpng16.16.1.0.so
LOCAL_MODULE_FILENAME := libpng16

include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := libdevil
LOCAL_CPP_FEATURES := exceptions
LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/${LIBPNG_INCLUDE_PATH} \
	$(LOCAL_PATH)/${LIBDEVIL_SRC_PATH}/include \
	$(LOCAL_PATH)/${LIBDEVIL_SRC_PATH}/src-IL/include
LOCAL_SRC_FILES := \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_wdp.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_cut.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_fits.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_jpeg.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_quantizer.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_fastconv.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_xpm.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pcx.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_raw.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_internal.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_dds.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_dds-save.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_manip.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_targa.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_stack.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_size.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_psp.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_squish.cpp \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_files.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_convbuff.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_rot.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_sun.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_bits.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_bmp.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_doom.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_wal.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_mdl.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_ftx.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_exr.cpp \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_alloc.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_profiles.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pnm.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_dicom.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_rle.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_mng.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_dcx.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_tiff.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pxr.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_icon.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_dpx.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_header.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_blp.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_png.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_main.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_wbmp.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_sgi.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_iff.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_error.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_neuquant.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_convert.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_icns.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/altivec_common.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_mp3.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_utility.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_tpl.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pcd.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_states.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_iwi.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_texture.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_gif.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_vtf.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_utx.cpp \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pic.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_psd.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_hdr.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_register.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_devil.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_lif.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_ilbm.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pal.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_io.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_rawdata.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_endian.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/altivec_typeconversion.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_pix.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_jp2.c \
	${LIBDEVIL_SRC_PATH}/src-IL/src/il_nvidia.cpp
LOCAL_SHARED_LIBRARIES := libpng

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := libdevilu
LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/${LIBDEVIL_SRC_PATH}/include \
	$(LOCAL_PATH)/${LIBDEVIL_SRC_PATH}/src-ILU/include
LOCAL_SRC_FILES := \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_alloc.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_error.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_filter.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_filter_rcg.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_internal.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_main.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_manip.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_mipmap.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_noise.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_region.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_rotate.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_scale.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_scale2d.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_scale3d.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_scaling.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_states.c \
	${LIBDEVIL_SRC_PATH}/src-ILU/src/ilu_utilities.c
LOCAL_SHARED_LIBRARIES := libdevil

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := libassdroid
LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/${LIBASSIMP_INCLUDE_PATH} \
	$(LOCAL_PATH)/${LIBDEVIL_SRC_PATH}/include
LOCAL_SRC_FILES := assdroid.cpp adScene.cpp adProgram.cpp net_airtheva_assdroid_Native.cpp
LOCAL_SHARED_LIBRARIES := libassimp libdevil libdevilu
LOCAL_LDLIBS := -llog -lGLESv2

include $(BUILD_SHARED_LIBRARY)
