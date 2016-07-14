package z.dotloopviewpager.other;

import java.io.Serializable;

/**
 * Created by max on 2016/6/19.
 */
public class ADBean implements Serializable {

    /**
     * img_url : http://demo.eboler.com/data/comads/2016/06/09/1465437742922.jpg
     * detail_url : http://www.baidu.com
     */

    private String img_url;
    private String detail_url;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }
}
