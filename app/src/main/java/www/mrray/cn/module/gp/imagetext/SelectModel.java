package www.mrray.cn.module.gp.imagetext;

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 图片选择类型
 */
public enum SelectModel {
    SINGLE(PhotoPickerActivity.Companion.getMODE_SINGLE()),
    MULTI(PhotoPickerActivity.Companion.getMODE_MULTI());

    private int model;

    SelectModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return String.valueOf(this.model);
    }
}
