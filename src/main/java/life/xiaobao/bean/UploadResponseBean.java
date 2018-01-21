package life.xiaobao.bean;

import lombok.Data;

/**
 * @author yangyongli
 */
@Data
public class UploadResponseBean {
    private boolean success = true;
    private String msg = "";
    private String file_path = "";
}


