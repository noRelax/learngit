package com.ehome.core.filepool;

import java.io.File;

/**
 * simple implements
 * @author hsu
 *
 */
public class HomeFilePool implements FilePool {

    private File home;
    private long cursor;
    private long size;
    
    public HomeFilePool(String homePath){
           this(homePath, 0);
    }
    
    public HomeFilePool(String homePath, long size) {
        this.size = size;
    }
    

    @Override
    public long current() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasFile(long fId, String suffix) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public File removeFile(long fId, String suffix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File createFile(String suffix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getFileId(File f) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public File getFile(long fId, String suffix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File returnFile(long fId, String suffix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasDir(long fId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public File removeDir(long fId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File createDir() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getDir(long fId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File returnDir(long fId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

}
