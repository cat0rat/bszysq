package com.mao.lang;

import java.util.Arrays;

public abstract class AbsStrBuilder implements Appendable, CharSequence {
	
    char[] value;
    int count;
    
    AbsStrBuilder() {
    }
    AbsStrBuilder(int capacity) {
        value = new char[capacity];
    }
    public int length() {
        return count;
    }
    
    /** 汉字占两个字节, 拉丁字母占1个字节的方式计算长度 */
    public int gbkLength(){
    	int c = 0;
    	for(int i = 0; i < count; i++){
    		if(value[i] > 710){
    			c++;
    		}
    		c++;
    	}
    	return c;
    }
    
    /** 汉字占两个字节, 拉丁字母占1个字节的方式计算长度 */
    public String gbkSubstring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        int c = start;
        int e = 0;
        for(int i = start; c < end; i++){
    		if(value[i] > 710){
    			c++;
    		}
    		c++;
    		e++;
    		if (e > count){
                throw new StringIndexOutOfBoundsException(end);
    		}
    	}
        return new String(value, start, e);
    }

    public int capacity() {
        return value.length;
    }
    
    // TODO 新增的功能
    
    boolean outNull = true;	// 默认输出null的值
	String nullStrVal = "null";	//值为null时输出的内容
	/** 设置String=null时使用的值, 如 */
	public void setNullStrVal(String nullStrVal) {
		this.nullStrVal = nullStrVal;
		setNullStrVal("");
	}
	/** false: 不输出 null, 默认输出"null" */
	public void setOutNull(boolean outNull) {
		this.outNull = outNull;
	}
	
	// TODO 字节操作
	
	final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
        99999999, 999999999, Integer.MAX_VALUE };

	/** 计算一个整数的字符串形式长度 */
	public static int stringSize(int x) {
		for (int i=0; ; i++)
			if (x <= sizeTable[i])
				return i+1;
	}
	
	/** 计算一个长整数的字符串形式长度 */
	public static int stringSize(long x) {
        long p = 10;
        for (int i=1; i<19; i++) {
            if (x < p)
                return i;
            p = 10*p;
        }
        return 19;
    }
	
	/**
     * All possible chars for representing a number as a String
     */
    final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
	
	public final static char [] DigitTens = {
        '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
        '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
        '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
        '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
        '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
        '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
        '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
        '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
        '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
        '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
        } ;

	public final static char [] DigitOnes = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        } ;
	
	/** 将一个整数的字符串形式复制到字符数组中 */
	public static void getChars(int i, int index, char[] buf) {
        int q, r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
        // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf [--charPos] = DigitOnes[r];
            buf [--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (;;) {
            q = (i * 52429) >>> (16+3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf [--charPos] = digits [r];
            i = q;
            if (i == 0) break;
        }
        if (sign != 0) {
            buf [--charPos] = sign;
        }
    }
	
	/** 将一个长整数的字符串形式复制到字符数组中 */
	public static void getChars(long i, int index, char[] buf) {
        long q;
        int r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (i > Integer.MAX_VALUE) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
            i = q;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int)i;
        while (i2 >= 65536) {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        for (;;) {
            q2 = (i2 * 52429) >>> (16+3);
            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
            buf[--charPos] = digits[r];
            i2 = q2;
            if (i2 == 0) break;
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }
	
	public static int indexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
	
	public static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {
        /*
         * Check arguments; return immediately where possible. For
         * consistency, don't check for null str.
         */
        int rightIndex = sourceCount - targetCount;
        if (fromIndex < 0) {
            return -1;
        }
        if (fromIndex > rightIndex) {
            fromIndex = rightIndex;
        }
        /* Empty string always matches. */
        if (targetCount == 0) {
            return fromIndex;
        }

        int strLastIndex = targetOffset + targetCount - 1;
        char strLastChar = target[strLastIndex];
        int min = sourceOffset + targetCount - 1;
        int i = min + fromIndex;

        startSearchForLastChar:
        while (true) {
            while (i >= min && source[i] != strLastChar) {
                i--;
            }
            if (i < min) {
                return -1;
            }
            int j = i - 1;
            int start = j - (targetCount - 1);
            int k = strLastIndex - 1;

            while (j > start) {
                if (source[j--] != target[k--]) {
                    i--;
                    continue startSearchForLastChar;
                }
            }
            return start - sourceOffset + 1;
        }
    }
    
	// TODO 数据完整性控制方法
	
    /**
     * 确保 缓存的最小容量
     * @param minCapacity (int) 最小容量
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > 0)
            ensureCapacityInternal(minCapacity);
    }

    /**
     * This method has the same contract as ensureCapacity, but is
     * never synchronized.
     */
    public void ensureCapacityInternal(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - value.length > 0)
            expandCapacity(minCapacity);
    }

    /** 扩展容量, 新的容量大小不一定是minCapacity */
    void expandCapacity(int minCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity < 0) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }

    /** 去掉未有值的容量空间 */
    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    /** 设置新的长度 */
    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            for (; count < newLength; count++)
                value[count] = '\0';
        } else {
            count = newLength;
        }
    }

    public char charAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        return value[index];
    }

    public int codePointAt(int index) {
        if ((index < 0) || (index >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return Character.codePointAt(value, index);
    }

    public int codePointBefore(int index) {
        int i = index - 1;
        if ((i < 0) || (i >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return Character.codePointBefore(value, index);
    }

//    public int codePointCount(int beginIndex, int endIndex) {
//        if (beginIndex < 0 || endIndex > count || beginIndex > endIndex) {
//            throw new IndexOutOfBoundsException();
//        }
//        return Character.codePointCountImpl(value, beginIndex, endIndex-beginIndex);
//    }

//    public int offsetByCodePoints(int index, int codePointOffset) {
//        if (index < 0 || index > count) {
//            throw new IndexOutOfBoundsException();
//        }
//        return Character.offsetByCodePointsImpl(value, 0, count,
//                                                index, codePointOffset);
//    }

    /** 将指定区域的字符复制到另一个字符数组中 */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    public void setCharAt(int index, char ch) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value[index] = ch;
    }

    public AbsStrBuilder append(Object obj) {
    	if (obj == null){
    		if(outNull){
    			return append(nullStrVal);
    		}
    		return this;
    	}
        return append(String.valueOf(obj));
    }
    
    /** 追加一个字符串, 可以通过outNull和nullStrVal来控制null值的输出 */
    public AbsStrBuilder append(String str) {
    	if(str == null && outNull){
    		str = nullStrVal;
    	}
    	if(str != null && str.length() > 0){
    		int len = str.length();
	        ensureCapacityInternal(count + len);
	        str.getChars(0, len, value, count);
	        count += len;
    	}
        return this;
    }

    public AbsStrBuilder append(StringBuffer sb) {
        if (sb == null){
        	if(outNull){
        		return append(nullStrVal);
        	}
        }else if(sb.length() > 0){
	        int len = sb.length();
	        ensureCapacityInternal(count + len);
	        sb.getChars(0, len, value, count);
	        count += len;
        }
        return this;
    }

    public AbsStrBuilder append(CharSequence s) {
    	if (s == null){
    		if(outNull){
    			return append(nullStrVal);
    		}
    		return this;
    	}
        if (s instanceof String)
            return this.append((String)s);
        if (s instanceof StringBuffer)
            return this.append((StringBuffer)s);
        return this.append(s, 0, s.length());
    }

    public AbsStrBuilder append(CharSequence s, int start, int end) {
    	if (s == null){
    		if(outNull){
    			return append(nullStrVal);
    		}
    		return this;
    	}
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        for (int i = start, j = count; i < end; i++, j++)
            value[j] = s.charAt(i);
        count += len;
        return this;
    }

    public AbsStrBuilder append(char[] str) {
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(str, 0, value, count, len);
        count += len;
        return this;
    }

    public AbsStrBuilder append(char str[], int offset, int len) {
        if (len > 0)                // let arraycopy report AIOOBE for len < 0
            ensureCapacityInternal(count + len);
        System.arraycopy(str, offset, value, count, len);
        count += len;
        return this;
    }

    public AbsStrBuilder append(boolean b) {
        if (b) {
            ensureCapacityInternal(count + 4);
            value[count++] = 't';
            value[count++] = 'r';
            value[count++] = 'u';
            value[count++] = 'e';
        } else {
            ensureCapacityInternal(count + 5);
            value[count++] = 'f';
            value[count++] = 'a';
            value[count++] = 'l';
            value[count++] = 's';
            value[count++] = 'e';
        }
        return this;
    }

    public AbsStrBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    public AbsStrBuilder append(int i) {
        if (i == Integer.MIN_VALUE) {
            append("-2147483648");
            return this;
        }
        int appendedLength = (i < 0) ? stringSize(-i) + 1
                                     : stringSize(i);
        int spaceNeeded = count + appendedLength;
        ensureCapacityInternal(spaceNeeded);
        getChars(i, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    public AbsStrBuilder append(long l) {
        if (l == Long.MIN_VALUE) {
            append("-9223372036854775808");
            return this;
        }
        int appendedLength = (l < 0) ? stringSize(-l) + 1
                                     : stringSize(l);
        int spaceNeeded = count + appendedLength;
        ensureCapacityInternal(spaceNeeded);
        getChars(l, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    public AbsStrBuilder append(float f) {
    	this.append(f + "");
        //new FloatingDecimal(f).appendTo(this);
        return this;
    }

    public AbsStrBuilder append(double d) {
    	this.append(d + "");
        //new FloatingDecimal(d).appendTo(this);
        return this;
    }

    public AbsStrBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, start+len, value, start, count-end);
            count -= len;
        }
        return this;
    }

//    public AbsStrBuilder appendCodePoint(int codePoint) {
//        final int count = this.count;
//
//        if (Character.isBmpCodePoint(codePoint)) {
//            ensureCapacityInternal(count + 1);
//            value[count] = (char) codePoint;
//            this.count = count + 1;
//        } else if (Character.isValidCodePoint(codePoint)) {
//            ensureCapacityInternal(count + 2);
//            Character.toSurrogates(codePoint, value, count);
//            this.count = count + 2;
//        } else {
//            throw new IllegalArgumentException();
//        }
//        return this;
//    }

    public AbsStrBuilder deleteCharAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        System.arraycopy(value, index+1, value, index, count-index-1);
        count--;
        return this;
    }

    public AbsStrBuilder replace(int start, int end, String str) {
    	if(str == null){
    		if(outNull){
    			str = nullStrVal;
    		}else{
    			return this;
    		}
    	}
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        
        if(len == 0){
        	delete(start, end);
        	return this;
        }
        
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);

        System.arraycopy(value, end, value, start + len, count - end);
        str.getChars(0, str.length(), value, start);
        count = newCount;
        return this;
    }

    public String substring(int start) {
        return substring(start, count);
    }

    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }
    
    public String substring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, start, end - start);
    }

    public AbsStrBuilder insert(int index, char[] str, int offset, int len) {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException(
                "offset " + offset + ", len " + len + ", str.length "
                + str.length);
        ensureCapacityInternal(count + len);
        System.arraycopy(value, index, value, index + len, count - index);
        System.arraycopy(str, offset, value, index, len);
        count += len;
        return this;
    }

    public AbsStrBuilder insert(int offset, Object obj) {
        return insert(offset, String.valueOf(obj));
    }

    public AbsStrBuilder insert(int offset, String str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null){
    		if(outNull){
    			return append(nullStrVal);
    		}
    		return this;
    	}
        int len = str.length();
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        str.getChars(0, str.length(), value, offset);
        count += len;
        return this;
    }

    public AbsStrBuilder insert(int offset, char[] str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        System.arraycopy(str, 0, value, offset, len);
        count += len;
        return this;
    }

    public AbsStrBuilder insert(int dstOffset, CharSequence s) {
    	if (s == null){
    		if(outNull){
    			return append(nullStrVal);
    		}
    		return this;
    	}
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

     public AbsStrBuilder insert(int dstOffset, CharSequence s,
                                         int start, int end) {
    	 if (s == null){
     		if(outNull){
     			return append(nullStrVal);
     		}
     		return this;
     	}
        if ((dstOffset < 0) || (dstOffset > this.length()))
            throw new IndexOutOfBoundsException("dstOffset "+dstOffset);
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, dstOffset, value, dstOffset + len,
                         count - dstOffset);
        for (int i=start; i<end; i++)
            value[dstOffset++] = s.charAt(i);
        count += len;
        return this;
    }

    public AbsStrBuilder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    public AbsStrBuilder insert(int offset, char c) {
        ensureCapacityInternal(count + 1);
        System.arraycopy(value, offset, value, offset + 1, count - offset);
        value[offset] = c;
        count += 1;
        return this;
    }

    public AbsStrBuilder insert(int offset, int i) {
        return insert(offset, String.valueOf(i));
    }

    public AbsStrBuilder insert(int offset, long l) {
        return insert(offset, String.valueOf(l));
    }

    public AbsStrBuilder insert(int offset, float f) {
        return insert(offset, String.valueOf(f));
    }

    public AbsStrBuilder insert(int offset, double d) {
        return insert(offset, String.valueOf(d));
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return indexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return lastIndexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
    }

    public AbsStrBuilder reverse() {
        boolean hasSurrogate = false;
        int n = count - 1;
        for (int j = (n-1) >> 1; j >= 0; --j) {
            char temp = value[j];
            char temp2 = value[n - j];
            if (!hasSurrogate) {
                hasSurrogate = (temp >= Character.MIN_SURROGATE && temp <= Character.MAX_SURROGATE)
                    || (temp2 >= Character.MIN_SURROGATE && temp2 <= Character.MAX_SURROGATE);
            }
            value[j] = temp2;
            value[n - j] = temp;
        }
        if (hasSurrogate) {
            // Reverse back all valid surrogate pairs
            for (int i = 0; i < count - 1; i++) {
                char c2 = value[i];
                if (Character.isLowSurrogate(c2)) {
                    char c1 = value[i + 1];
                    if (Character.isHighSurrogate(c1)) {
                        value[i++] = c1;
                        value[i] = c2;
                    }
                }
            }
        }
        return this;
    }

    public abstract String toString();

    public char[] getValue() {
        return value;
    }

}
