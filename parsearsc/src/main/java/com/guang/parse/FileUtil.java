package com.guang.parse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;



/**
 * 文件工具类
 *
 * @author huanghongyu
 */
public class FileUtil {

    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    private static String mTag = "FileUtil";

    /**
     * Returns a human-readable version of the file size, where the input
     * represents a specific number of bytes.
     *
     * @param size the number of bytes
     * @return a human-readable display value (includes units)
     */
    public static String formatSize(long size) {
        float ONE_KB = 1024F;
        float ONE_MB = ONE_KB * ONE_KB;
        float ONE_GB = ONE_KB * ONE_MB;
        String displaySize;
        DecimalFormat df = new DecimalFormat("0.00");
        if (size >= ONE_KB && size < ONE_MB) {
            displaySize = String.valueOf(df.format(size / ONE_KB)) + " KB";
        } else if (size >= ONE_MB && size < ONE_GB) {
            displaySize = String.valueOf(df.format(size / ONE_MB)) + " MB";
        } else if (size >= ONE_GB) {
            displaySize = String.valueOf(df.format(size / ONE_GB)) + " GB";
        } else {
            displaySize = String.valueOf(df.format(size)) + " B";
        }
        return displaySize;
    }

    /**
     * 递归删除文件目录
     *
     * @param dir 文件目录
     */
    public static void deleteFileDir(File dir) {
        try {
            if (dir.exists() && dir.isDirectory()) {// 判断是文件还是目录
                if (dir.listFiles().length == 0) {// 若目录下没有文件则直接删除
                    dir.delete();
                } else {// 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = dir.listFiles();
                    int len = dir.listFiles().length;
                    for (int j = 0; j < len; j++) {
                        if (delFile[j].isDirectory()) {
                            deleteFileDir(delFile[j]);// 递归调用deleteFileDir方法并取得子目录路径
                        } else {
                            boolean isDeltet = delFile[j].delete();// 删除文件
                        }
                    }
                    delFile = null;
                }
                //deleteFileDir(dir);// 递归调用
                if (dir.listFiles().length == 0) {// 若目录下没有文件则直接删除
                    dir.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除兄弟文件或者文件夹
     *
     * @param parentDir
     * @param file
     */
    public static void deleteBrotherFile(File parentDir, File file) {
        if (parentDir == null || !parentDir.isDirectory() || !parentDir.exists())
            return;
        try {
            File delFile[] = parentDir.listFiles();
            int len = parentDir.listFiles().length;
            for (int j = 0; j < len; j++) {
                if (file == null || !file.exists() || !file.getAbsolutePath().equals(delFile[j].getAbsolutePath())) {
                    if (delFile[j].isDirectory()) {
                        deleteFileDir(delFile[j]);// 递归调用deleteFileDir方法并取得子目录路径
                    } else {
                        delFile[j].delete();
                    }
                }

            }
        } catch (Exception e) {

        }


    }

    /**
     * 删除单个文件
     *
     */
    public static boolean deleteFile(File file) {
        try {
            if (file != null && file.isFile() && file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile 源文件
     * @param destFile   目标文件
     * @return 是否拷贝成功
     */
    public static boolean copyFile(File sourceFile, File destFile) {
        boolean isCopyOk = false;
        byte[] buffer = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        // 如果此时没有文件夹目录就创建
        String canonicalPath = "";
        try {
            canonicalPath = destFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!destFile.exists()) {
            if (canonicalPath.lastIndexOf(File.separator) >= 0) {
                canonicalPath = canonicalPath.substring(0, canonicalPath.lastIndexOf(File.separator));
                File dir = new File(canonicalPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
        }

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFile), DEFAULT_BUFFER_SIZE);
            bos = new BufferedOutputStream(new FileOutputStream(destFile), DEFAULT_BUFFER_SIZE);
            buffer = new byte[DEFAULT_BUFFER_SIZE];
            int len = 0;
            while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            isCopyOk = sourceFile != null && sourceFile.length() == destFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                buffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isCopyOk;
    }

    /**
     * 读取文件内容到字节数组
     *
     * @param file
     * @return
     */
    public static byte[] readFileToBytes(File file) {
        byte[] bytes = null;
        if (file.exists()) {
            byte[] buffer = null;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            ByteArrayOutputStream baos = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
                baos = new ByteArrayOutputStream();
                bos = new BufferedOutputStream(baos, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                bytes = baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                        bos = null;
                    }
                    if (baos != null) {
                        baos.close();
                        baos = null;
                    }
                    if (bis != null) {
                        bis.close();
                        bis = null;
                    }
                    buffer = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 读取文件内容到字节数组
     *
     * @param file
     * @param offset
     * @param len
     * @return
     */
    public static byte[] readFileToBytes(File file, long offset, long len) {
        byte[] bytes = null;
        if (file.exists() && offset >= 0 && len > offset && offset < file.length()) {
            RandomAccessFile raf = null;
            ByteArrayOutputStream bos = null;
            try {
                raf = new RandomAccessFile(file, "r");
                raf.seek(offset);
                bos = new ByteArrayOutputStream();
                int b = -1;
                long count = offset;
                while ((b = raf.read()) != -1 && count < len) {
                    bos.write(b);
                    count++;
                }
                bos.flush();
                bytes = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                        raf = null;
                    }
                    if (bos != null) {
                        bos.close();
                        bos = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * 将字节写入到文件
     *
     * @param file
     * @param bytes
     * @param offset
     * @return
     */
    public static boolean writeBytesToFile(File file, byte[] bytes, long offset) {
        boolean isOk = false;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.exists() && bytes != null && offset >= 0) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "rw");
                raf.seek(offset);
                raf.write(bytes);
                isOk = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                        raf = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isOk;
    }

    /**
     * 读取文件内容到字符串
     *
     * @param file
     * @return
     */
    public static String readFileToString(File file) {
        return readFileToString(file, null);
    }

    /**
     * 读取文件内容到字符串
     *
     * @param file
     * @param encoding
     * @return
     */
    public static String readFileToString(File file, String encoding) {
        String result = null;
        if (file.exists()) {
            char[] buffer = null;
            BufferedReader br = null;
            InputStreamReader isr = null;
            BufferedWriter bw = null;
            StringWriter sw = new StringWriter();
            try {
                isr = encoding == null ? new InputStreamReader(new FileInputStream(file)) : new InputStreamReader(
                        new FileInputStream(file), encoding);
                br = new BufferedReader(isr);
                bw = new BufferedWriter(sw);
                buffer = new char[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = br.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bw.write(buffer, 0, len);
                }
                bw.flush();
                result = sw.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                        bw = null;
                    }
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                    if (isr != null) {
                        isr.close();
                        isr = null;
                    }
                    if (sw != null) {
                        sw.close();
                        sw = null;
                    }
                    buffer = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 从输入流中读取字符串
     * @param in
     * @param encoding
     * @return
     */
    public static String readInputStreamToString(InputStream in,String encoding){
        String result = null;

            char[] buffer = null;
            BufferedReader br = null;
            InputStreamReader isr = null;
            BufferedWriter bw = null;
            StringWriter sw = new StringWriter();
            try {
                isr = encoding == null ? new InputStreamReader(in) : new InputStreamReader(
                        in, encoding);
                br = new BufferedReader(isr);
                bw = new BufferedWriter(sw);
                buffer = new char[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = br.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bw.write(buffer, 0, len);
                }
                bw.flush();
                result = sw.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                        bw = null;
                    }
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                    if (isr != null) {
                        isr.close();
                        isr = null;
                    }
                    if (sw != null) {
                        sw.close();
                        sw = null;
                    }
                    buffer = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return result;
    }
    /**
     * 写字符串到文件，文件父目录如果不存在，会自动创建
     *
     * @param file
     * @param content
     * @return
     */
    public static boolean writeStringToFile(File file, String content) {
        return writeStringToFile(file, content, false);
    }

    /**
     * 写字符串到文件，文件父目录如果不存在，会自动创建
     *
     * @param file
     * @param content
     * @param isAppend
     * @return
     */
    public static boolean writeStringToFile(File file, String content, boolean isAppend) {
        boolean isWriteOk = false;
        char[] buffer = null;
        int count = 0;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                br = new BufferedReader(new StringReader(content));
                bw = new BufferedWriter(new FileWriter(file, isAppend));
                buffer = new char[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = br.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bw.write(buffer, 0, len);
                    count += len;
                }
                bw.flush();
            }
            isWriteOk = content.length() == count;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    bw = null;
                }
                if (br != null) {
                    br.close();
                    br = null;
                }
                buffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isWriteOk;
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param file
     * @param bytes
     * @return
     */
    public static boolean writeBytesToFile(File file, byte[] bytes) {
        return writeBytesToFile(file, bytes, false);
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param file
     * @param bytes
     * @param isAppend
     * @return
     */
    public static boolean writeBytesToFile(File file, byte[] bytes, boolean isAppend) {
        boolean isWriteOk = false;
        byte[] buffer = null;
        int count = 0;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                bos = new BufferedOutputStream(new FileOutputStream(file, isAppend), DEFAULT_BUFFER_SIZE);
                bais = new ByteArrayInputStream(bytes);
                bis = new BufferedInputStream(bais, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                    count += len;
                }
                bos.flush();
            }
            isWriteOk = bytes.length == count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (bais != null) {
                    bais.close();
                    bais = null;
                }
                buffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isWriteOk;
    }

    /**
     * 创建文件父目录
     *
     * @param file
     * @return
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
            }
        }
        return isMkdirs;
    }

    /**
     * 创建文件及其父目录
     *
     * @param file
     * @return
     */
    public static boolean createNewFileAndParentDir(File file) {
        boolean isCreateNewFileOk = true;
        isCreateNewFileOk = createParentDir(file);
        //创建父目录失败，直接返回false，不再创建子文件
        if (isCreateNewFileOk) {
            if (!file.exists()) {
                try {
                    isCreateNewFileOk = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    isCreateNewFileOk = false;
                }
            }
        }
        return isCreateNewFileOk;
    }
    /**
     * 获取文件的MD5码
     *
     * @param byteArray 文件的byte数组
     * @return MD5
     * @author 黄宏宇
     */
    public static String getMD5(byte[] byteArray) {
        MessageDigest mMessageDigest = null;
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mMessageDigest != null) {
            mMessageDigest.update(byteArray, 0, byteArray.length);
            return bytesToHexString(mMessageDigest.digest());
        }
        return null;
    }

    /**
     * 通过文件获取md5值
     *
     * @param file 文件
     * @return MD5
     */
    public static String getMD5(File file) {
        MessageDigest mMessageDigest = null;
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mMessageDigest != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[8192];
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    mMessageDigest.update(buffer, 0, length);
                }
                return bytesToHexString(mMessageDigest.digest());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * byte数组转成字符串
     *
     * @param bytes byte数组
     * @return 字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(hv);
            }
            return stringBuffer.toString();
        }
        return null;
    }

    /**
     * 通过byte数组取到short
     *
     * @param bytes
     * @param index 第几位开始取
     * @return
     */
    public static short getShort(byte[] bytes, int index) {
        return (short) (((bytes[index + 1] << 8) | bytes[index + 0] & 0xff));
    }

    public static byte getWaveLevel(short[] in, int pcmFrameSize) {
        byte waveLevel;
        short minlevel = 0;
        short maxlevel = 0;

        minlevel = 0;
        maxlevel = 0;
        for (int j = 0; j < pcmFrameSize; ++j) {
            short w = in[j];
            if (w > maxlevel) {
                maxlevel = w;
            }
            if (w < minlevel) {
                minlevel = w;
            }
        }
        if (Math.abs(maxlevel) > Math.abs(minlevel)) {
            waveLevel = (byte) (maxlevel >> 8);
        } else {
            waveLevel = (byte) (minlevel >> 8);
        }

        return waveLevel;
    }

    public static long getRemoteFileSize(String urlString) throws IOException, Exception {
        long lenght = 0;
        URL mUrl = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
      /*  conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("Referer", url);
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");*/
        conn.connect();

        int responseCode = conn.getResponseCode();
        // 判断请求是否成功处理
        if (responseCode == 200) {
            lenght = conn.getContentLength();
        }

        return lenght;
    }
}
