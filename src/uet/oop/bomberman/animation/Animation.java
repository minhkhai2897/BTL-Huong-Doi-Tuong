package uet.oop.bomberman.animation;

import javafx.scene.image.Image;

import java.util.List;

public class Animation {
    private int count = 0;
    protected static final int numberOfFrames = 8;
    /**
     * Truyen vao mot danh sach cac anh va sau đó tra ve anh duoc chon.
     * sau numberOfFrames khung hình thì sẽ thay 1 anh moi. den het thi lai quay ve anh dau
     * @param list danh sach cac anh
     * @return
     */
    protected Image handle(List<Image> list) {
        Image img = (list.get(count / numberOfFrames));
        count++;
        if (count >= numberOfFrames * list.size()) {
            count = 0;
        }
        return img;
    }
}
