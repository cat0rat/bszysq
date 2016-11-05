package com.mao.cmpr;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Zip压缩/解压缩
 * @author Mao 2015年11月14日 上午10:09:06
 */
public class ZipCmpr {

	private OutputStream out = null;
    private BufferedOutputStream bos = null;
    private ZipArchiveOutputStream zaos = null;
    private String zipFileName = null;
    
    private Set<String> exclDirs = null;	//排除的文件夹
    private Set<String> exclFiles = null;	//排除的文件
    
    private Set<String> exclDirNames = null;	//排除的文件夹名
  
    public ZipCmpr(String zipname) {
        this.zipFileName = zipname;
    }
  
    /** 创建压缩流 */
    public void createZipOut() throws FileNotFoundException, IOException {
        File f = new File(zipFileName);
        File dir = f.getParentFile();
        if(!dir.exists()){
        	dir.mkdirs();
        }
        out = new FileOutputStream(f);
        bos = new BufferedOutputStream(out);
        zaos = new ZipArchiveOutputStream(bos);
        zaos.setEncoding("GBK");
    }
  
    /** 关闭压缩流 */
    public void closeZipOut() throws Exception {
        zaos.flush();
        zaos.close();
  
        bos.flush();
        bos.close();
  
        out.flush();
        out.close();
    }
  
    /**
     * 把一个目录打包到zip文件中的某目录
     * @param dirpath (String) 目录绝对地址
     * @param pathName (String) zip中目录
     */
    public void packToolFiles(String dirpath, String pathName) throws FileNotFoundException, IOException {
        if (StringUtils.isNotEmpty(pathName)) {
            pathName = pathName + File.separator;
        }
        packToolFiles(zaos, dirpath, pathName);
    }
  
    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param dirpath (String) 目录绝对地址
     * @param pathName (String) zip文件抽象地址
     */
    public void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws FileNotFoundException, IOException {
  
        ByteArrayOutputStream tempbaos = new ByteArrayOutputStream();
        BufferedOutputStream tempbos = new BufferedOutputStream(tempbaos);
  
        File dir = new File(dirpath);
        //返回此绝对路径下的文件
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        File t_f = null;
        String t_fn = null;
        String t_fap = null;
        String t_fpn = null;
        for (int i = 0; i < files.length; i++) {
        	t_f = files[i];
        	t_fn = t_f.getName();
        	
        	// 是否是排除的文件名
        	if(isExclDirName(t_fn)){
        		continue;
        	}
        	
        	t_fap = t_f.getAbsolutePath();
        	t_fpn = pathName + t_fn;
            
        	//判断此文件是否是一个文件夹
            if (t_f.isDirectory()) {
            	// 是否是排除的文件夹
            	if(!isExclDir(t_fpn)){
            		packToolFiles(zaos, t_fap, t_fpn + File.separator);
            	}
            } else {
            	// 是否是排除的文件
            	if(!isExclFile(t_fpn)){
	                zaos.putArchiveEntry(new ZipArchiveEntry(t_fpn));
	                IOUtils.copy(new FileInputStream(t_fap), zaos);
	                zaos.closeArchiveEntry();
            	}
  
            }
  
        }
  
        tempbaos.flush();
        tempbaos.close();
  
        tempbos.flush();
        tempbos.close();
    }
  
    /**
     * 把一个zip文件解压到一个指定的目录中
     * @param zipfilename (String) zip文件绝对地址
     * @param outputdir (String) 目录绝对地址
     */
    public static void unZipToFolder(String zipfilename, String outputdir) throws IOException {
        File zipfile = new File(zipfilename);
        if (zipfile.exists()) {
            outputdir = outputdir + File.separator;
            FileUtils.forceMkdir(new File(outputdir));
  
            @SuppressWarnings("resource")
			ZipFile zf = new ZipFile(zipfile, "GBK");
            Enumeration<ZipArchiveEntry> zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                } else {
                    IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName())));
                }
            }
        } else {
            throw new IOException("指定的解压文件不存在：\t" + zipfilename);
        }
    }
    
    // TODO 排除方法
    
    /**
     * 需要排除的文件夹名(相对根)
     * @param dir
     */
    public void exclDir(String dir){
    	if(exclDirs == null){
    		exclDirs = new HashSet<String>();
    	}
    	exclDirs.add(dir);
    }
    
    /**
     * 是否是排除的文件夹名(相对根)
     * @param dir
     */
    public boolean isExclDir(String dir){
    	if(exclDirs != null){
    		return exclDirs.contains(dir);
    	}
    	return false;
    }
    
    /**
     * 需要排除的文件夹名
     * @param dir
     */
    public void exclDirName(String dir){
    	if(exclDirNames == null){
    		exclDirNames = new HashSet<String>();
    	}
    	exclDirNames.add(dir);
    }
    
    /**
     * 是否是排除的文件夹名
     * @param dir
     */
    public boolean isExclDirName(String dir){
    	if(exclDirNames != null){
    		return exclDirNames.contains(dir);
    	}
    	return false;
    }
    
    /**
     * 需要排除的文件路径名(相对根)
     * @param fp
     */
    public void exclFile(String fp){
    	if(exclFiles == null){
    		exclFiles = new HashSet<String>();
    	}
    	exclFiles.add(fp);
    }
    
    /**
     * 是否是排除的文件路径名(相对根)
     * @param fp
     */
    public boolean isExclFile(String fp){
    	if(exclFiles != null){
    		return exclFiles.contains(fp);
    	}
    	return false;
    }
    
    // TODO 测试
    
    public static void main(String[] args) throws FileNotFoundException, Exception {
		String base_src = "E:/temp/zip/jzs";
		String base_tar = "E:/temp/zip/jzs.zip";
		ZipCmpr cmpr = new ZipCmpr(base_tar);
		cmpr.createZipOut();
		cmpr.packToolFiles(base_src, "jzs");
		cmpr.closeZipOut();

		ZipCmpr.unZipToFolder(base_tar, base_src + "2");
    }
}
