package shopping.util;

import sun.awt.windows.WBufferStrategy;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * 文件帮助类
 */
public class FileData extends File {

    public FileData(String path){
        super(path);
    };

    public FileData(File file){
        super(file.getPath());
    }

    /**
     * 导出数据
     * @param data  二维数据
     * @param names 列名
     * @return {boolean} 是否导出成功
     */
    public boolean Export(Vector<Vector<Object>> data, Vector<String> names) {
        if (!this.getPath().endsWith(".csv")){
            String path = this.getPath()+".csv";
            this.renameTo(new File(path));
        }
        try {
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(this), "gbk");
            for (String name : names) {
                ow.write(name);
                ow.write(",");
            }
            ow.write("\r\n");
            for (Vector<Object> orderItems : data) {
                for (Object orderItem : orderItems) {
                    if (orderItem != null) {
                        ow.write(orderItem.toString());
                    }
                    ow.write(",");
                }
                ow.write("\r\n");
            }
            ow.flush();
            ow.close();
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    /**
     * 导入csv数据，转为二维矢量
     *
     * @return Vectors 二维矢量
     */
    public Vector<Vector<Object>> Import() {
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        if (!this.getPath().endsWith(".csv")){
            String path = this.getPath()+".csv";
            this.renameTo(new File(path));
        }
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(this), "gbk");
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] objs = line.split(",");
                Vector<Object> lineData = new Vector<Object>(Arrays.asList(objs));
                data.add(lineData);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
