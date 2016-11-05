package com.mao.lang;

/**
 * 一个简易的 StringBuilder, 可以通过  outNull=false来控制不输出null。 <br>
 * 线程不安全的
 * @author Mao 2014-12-8 下午4:42:45
 */
public class StrBuilder extends AbsStrBuilder implements java.io.Serializable, CharSequence {

    static final long serialVersionUID = 1;

    public StrBuilder() {
        super(16);
    }
    
    public StrBuilder(boolean isOutNull) {
        this();
        setOutNull(isOutNull);
    }

    public StrBuilder(int capacity) {
        super(capacity);
    }

    public StrBuilder(int capacity, boolean isOutNull) {
        super(capacity);
        setOutNull(isOutNull);
    }

    public StrBuilder(String str) {
        super(str.length() + 16);
        append(str);
    }

    public StrBuilder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }

    public StrBuilder append(Object obj) {
    	super.append(obj);
        return this;
    }

    public StrBuilder append(String str) {
        super.append(str);
        return this;
    }
    

    public StrBuilder append(StrBuilder sb) {
        if (sb == null){
        	if(!outNull){
        		return append(nullStrVal);
        	}
        }else if(sb.length() > 0){
	        int len = sb.length();
	        int newcount = count + len;
	        ensureCapacityInternal(newcount);
	        sb.getChars(0, len, value, count);
	        count = newcount;
        }
        return this;
    }

    public StrBuilder append(StringBuffer sb) {
        super.append(sb);
        return this;
    }

    public StrBuilder append(CharSequence s) {
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
        if (s instanceof StrBuilder)
            return this.append((StrBuilder)s);
        return this.append(s, 0, s.length());
    }

    public StrBuilder append(CharSequence s, int start, int end) {
        super.append(s, start, end);
        return this;
    }

    public StrBuilder append(char[] str) {
        super.append(str);
        return this;
    }

    public StrBuilder append(char[] str, int offset, int len) {
        super.append(str, offset, len);
        return this;
    }

    public StrBuilder append(boolean b) {
        super.append(b);
        return this;
    }

    public StrBuilder append(char c) {
        super.append(c);
        return this;
    }

    public StrBuilder append(int i) {
        super.append(i);
        return this;
    }

    public StrBuilder append(long lng) {
        super.append(lng);
        return this;
    }

    public StrBuilder append(float f) {
        super.append(f);
        return this;
    }

    public StrBuilder append(double d) {
        super.append(d);
        return this;
    }

//    public StrBuilder appendCodePoint(int codePoint) {
//        super.appendCodePoint(codePoint);
//        return this;
//    }

    public StrBuilder delete(int start, int end) {
        super.delete(start, end);
        return this;
    }

    public StrBuilder deleteCharAt(int index) {
        super.deleteCharAt(index);
        return this;
    }

    public StrBuilder replace(int start, int end, String str) {
        super.replace(start, end, str);
        return this;
    }

    public StrBuilder insert(int index, char[] str, int offset,
                                int len){
        super.insert(index, str, offset, len);
        return this;
    }

    public StrBuilder insert(int offset, Object obj) {
        return insert(offset, String.valueOf(obj));
    }

    public StrBuilder insert(int offset, String str) {
        super.insert(offset, str);
        return this;
    }

    public StrBuilder insert(int offset, char[] str) {
        super.insert(offset, str);
        return this;
    }

    public StrBuilder insert(int dstOffset, CharSequence s) {
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

    public StrBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        super.insert(dstOffset, s, start, end);
        return this;
    }

    public StrBuilder insert(int offset, boolean b) {
        super.insert(offset, b);
        return this;
    }

    public StrBuilder insert(int offset, char c) {
        super.insert(offset, c);
        return this;
    }

    public StrBuilder insert(int offset, int i) {
        return insert(offset, String.valueOf(i));
    }

    public StrBuilder insert(int offset, long l) {
        return insert(offset, String.valueOf(l));
    }

    public StrBuilder insert(int offset, float f) {
        return insert(offset, String.valueOf(f));
    }

    public StrBuilder insert(int offset, double d) {
        return insert(offset, String.valueOf(d));
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return indexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
    }
    
    public int indexOf(char c, int fi) {
    	if(fi >= 0 && fi < count){
    		for(int i = fi; i < count; i++){
    			if(value[i] == c) return i;
    		}
    	}
    	return -1;
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return lastIndexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
    }

    public StrBuilder reverse() {
        super.reverse();
        return this;
    }

    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }

    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }

    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }
	
}
