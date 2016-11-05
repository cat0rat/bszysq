package com.mao.lang;

import java.io.IOException;
import java.io.Writer;

/**
 * 简易的 字符缓存输出流类
 * @author Mao 2014-12-8 上午11:35:57
 */
public class BuffWriter extends Writer {
	
	Writer out;	//字符流
    char cb[];	//缓存
    int nChars, nextChar;	//nChars: 缓存大小, nextChar: 字符计数器 
    static int defaultCharBufferSize = 8192;	//默认的缓存大小

    /** 行分隔符 */
    String lineSeparator;
	
	boolean outNull = true;	// 默认输出null的值
	String nullStrVal = "null";	//值为null时输出的内容
	int doubleDec = -1;	//double 保留的小数位
	long doubleTimes = 1;
	double doubleEnd = 0.5;	//用于四舍五入

	public BuffWriter(Writer out) {
		this(out, defaultCharBufferSize, true);
	}
	public BuffWriter(Writer out, boolean outNull) {
		this(out, defaultCharBufferSize, outNull);
	}
	public BuffWriter(Writer out, int sz, boolean outNull) {
		 super(out);
        if (sz <= 0)
            throw new IllegalArgumentException("Buffer size <= 0");
        this.out = out;
        cb = new char[sz];
        nChars = sz;
        nextChar = 0;
        this.outNull = outNull;

//        lineSeparator = java.security.AccessController.doPrivileged(
//            new sun.security.action.GetPropertyAction("line.separator"));
        lineSeparator = "\r\n";
	}
	
	/** 确保流是打开的 */
    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed");
    }
    
    /** 输出缓存 */
    void flushBuffer() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar == 0)
                return;
            out.write(cb, 0, nextChar);
            nextChar = 0;
        }
    }
    
    private int min(int a, int b) {
        return a < b ? a : b;
    }
	
	// TODO 方法
    
    /** 写一个字符 */
    public void write(int c) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar >= nChars)
                flushBuffer();
            cb[nextChar++] = (char) c;
        }
    }
    
    /** 写一组字符 */
    public void write(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }

            if (len >= nChars) {
                flushBuffer();
                out.write(cbuf, off, len);
                return;
            }

            int b = off, t = off + len;
            while (b < t) {
                int d = min(nChars - nextChar, t - b);
                System.arraycopy(cbuf, b, cb, nextChar, d);
                b += d;
                nextChar += d;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }
    
    /** 写一个字符串 */
    public void write(String s, int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();

            int b = off, t = off + len;
            while (b < t) {
                int d = min(nChars - nextChar, t - b);
                s.getChars(b, b + d, cb, nextChar);
                b += d;
                nextChar += d;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }
    
    /** 新的一行 */
    public void newLine() throws IOException {
        write(lineSeparator);
    }
    
    /** 输出缓存到文件 */
    public void flush() throws IOException {
        synchronized (lock) {
            flushBuffer();
            out.flush();
        }
    }
    
    /** 关闭输出流 */
    public void close() throws IOException {
        synchronized (lock) {
            if (out == null) {
                return;
            }
            try {
                flushBuffer();
            } finally {
                out.close();
                out = null;
                cb = null;
            }
        }
    }
	
    // TODO 增强方法

    
    /** 写一个字符 */
    public BuffWriter appendChar(char c) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar >= nChars)
                flushBuffer();
            cb[nextChar++] = c;
        }
        return this;
    }
    
	public Writer append(CharSequence csq) throws IOException {
        if (csq == null){
            if(outNull) write(nullStrVal);
        }else if(csq.length() != 0){
            write(csq.toString());
        }
        return this;
    }


    /** 写一个整数 */
    public BuffWriter appendInt(int i) throws IOException {
    	synchronized (lock) {
			ensureOpen(); // 确保输出流打开着
			// 计算整数和字符串形式长度
			int ilen = (i < 0) ? AbsStrBuilder.stringSize(-i) + 1 : AbsStrBuilder.stringSize(i);
			// 超出缓存, 则先输出缓存
			if (nextChar + ilen >= nChars) {
				flushBuffer();
			}
			nextChar += ilen;
			AbsStrBuilder.getChars(i, nextChar, cb);	//将一个整数的字符串形式复制到缓存中
        }
		return this;
    }

    /** 写一个整数, 长度不足len, 在后补0 */
    public BuffWriter appendIntAfter(int i, int len) throws IOException {
		synchronized (lock) {
			ensureOpen(); // 确保输出流打开着
			// 计算整数的字符串形式长度
			int ilen = (i < 0) ? AbsStrBuilder.stringSize(-i) + 1 : AbsStrBuilder.stringSize(i);
			// 超出缓存, 则先输出缓存
			if (nextChar + ilen >= nChars) {
				flushBuffer();
			}
			nextChar += ilen;
			AbsStrBuilder.getChars(i, nextChar, cb);	//将一个整数的字符串形式复制到缓存中
			
			len -= ilen;
			if (nextChar + len >= nChars) {
				flushBuffer();
			}
			if(len > 0){
				for(int k = 0; k < len; k++){
					cb[nextChar++] = '0';
				}
			}
        }
		return this;
    }

    /** 写一个长整数 */
    public BuffWriter appendLong(long l) throws IOException {
    	synchronized (lock) {
			ensureOpen(); // 确保输出流打开着
			// 计算长整数的字符串形式长度
			int ilen = (l < 0) ? AbsStrBuilder.stringSize(-l) + 1 : AbsStrBuilder.stringSize(l);
			// 超出缓存, 则先输出缓存
			if (nextChar + ilen >= nChars) {
				flushBuffer();
			}
			nextChar += ilen;
			AbsStrBuilder.getChars(l, nextChar, cb);	//将一个整数的字符串形式复制到缓存中
        }
		return this;
    }
	
	public BuffWriter appendDouble(double d) throws IOException {
		if(doubleDec < 0){
			Double D = d;
			write(D.toString());
		}else if(doubleDec == 0){
			appendInt((int)d);
		}else{
			d += doubleEnd;
			int zv = (int)d;
			int xv = (int)((d - zv) * doubleTimes);
			if(xv < 0) xv = -xv;
			if(zv == 0 && d < 0.0) append('-');
			appendInt(zv);
			append('.');
			appendIntAfter(xv, doubleDec);
		}
        return this;
    }
	
	// TODO 方便的方法


    public BuffWriter append(char c) throws IOException {
        return appendChar(c);
    }

    public BuffWriter append(double d) throws IOException {
    	return appendDouble(d);
    }
    
    public BuffWriter append(int i) throws IOException {
    	return appendInt(i);
    }
    
    public BuffWriter append(long l) throws IOException {
    	return appendLong(l);
    }

    public BuffWriter append(Character c) throws IOException {
        return appendChar(c);
    }
    
    public BuffWriter append(Double d) throws IOException {
    	if (d == null){
            if(outNull) write(nullStrVal);
        }else{
            return appendDouble(d);
        }
    	return this;
    }
    
    public BuffWriter append(Integer i) throws IOException {
    	if (i == null){
            if(outNull) write(nullStrVal);
        }else{
            return appendInt(i);
        }
    	return this;
    }
    
    public BuffWriter append(Long l) throws IOException {
    	if (l == null){
            if(outNull) write(nullStrVal);
        }else{
            return appendLong(l);
        }
    	return this;
    }
	
	// TODO get set
	
	
	/** 设置String=null时使用的值, 如 */
	public void setNullStrVal(String nullStrVal) {
		this.nullStrVal = nullStrVal;
		setNullStrVal("");
	}
	/** true: 不输出 null */
	public void setOutNull(boolean outNull) {
		this.outNull = outNull;
	}
	public int getDoubleDec() {
		return doubleDec;
	}
	public void setDoubleDec(int doubleDec) {
		if(doubleDec > 10) doubleDec = 11;
		this.doubleDec = doubleDec;
		doubleTimes = 1;
		doubleEnd = 0.5;
		for(int i = 0; i < doubleDec; i++){
			doubleTimes *= 10;
			doubleEnd /= 10.0;
		}
	}
	public boolean isOutNull() {
		return outNull;
	}
	public String getNullStrVal() {
		return nullStrVal;
	}
	
}
