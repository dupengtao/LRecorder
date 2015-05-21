package com.l.recorder.model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by zhangjiahao on 15-5-11.
 */
public class RecordFileManager {

    public void clearTempDir() {
        File dir = new File(Constant.TEMP_FILE_PATH);
        if (dir.exists()) {
            for (File temp : dir.listFiles()) {
                temp.delete();
            }
        }
    }

    public File getTempFile(String name) {
        File file = new File(getTempDir(), name);
        if (file.exists()) {
            if (file.delete())
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File getTempDir() {
        File dir = new File(Constant.TEMP_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public void merginTempFile(String fileName, ArrayList<File> mTempList, int offset) {
        if (offset != 6 || offset != 9) {
            offset = 6;
        }
        File file = new File(Constant.RECORDER_PATH, fileName);

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, true));
            for (int i = 0; i < mTempList.size(); i++) {
                bis = new BufferedInputStream(new FileInputStream(mTempList.get(i)));
                boolean first = true;
//                byte[] buff = new byte[bis.available()];
                byte[] buff = new byte[1024 * 1024 * 10];
                int len;
                if (i == 0) {
                    while ((len = bis.read(buff)) != -1) {
                        bos.write(buff, 0, len);
                    }
                } else {
                    while ((len = bis.read(buff)) != -1) {
                        if (first) {
                            bos.write(buff, offset, len - offset);
                            first = false;
                        } else {
                            bos.write(buff, 0, len);
                        }
                        bos.flush();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clearTempDir();
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
