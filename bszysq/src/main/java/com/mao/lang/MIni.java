package com.mao.lang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ini文件 解析类<br>
 * 因为 java.util.Properties会对中文件进行编码, 不方便<br>
 * 1.注释格式只有#一种; 2.另外[]会作为一个分组; 3.空白行原样输出。4.支持代码段<![CDATA[内容]]>
 * @author Mao 2014-5-20 下午9:57:48
 */
public class MIni {
	/** 键值对(k=v) --类型 */
	public static final int Type_KV = 1;
	/** 注释(#) --类型 */
	public static final int Type_Comment = 2;
	/** 分组([])--类型 */
	public static final int Type_Group = 3;
	
	public static String _R = "\r\n";
	public static String _Cdata_S = "<![CDATA[";
	public static String _Cdata_E = "]]>";
	
	public static String Orig_Back_Ext = "_Orig_Back_";
	
	/** 注释前缀符, 默认# */
	public char note_char = '#';
	/** 数据分割符, 默认= */
	public char data_split = '=';
	/** 类型开始符, 默认[ */
	public char type_split_s = '[';
	/** 类型结束符, 默认] */
	public char type_split_e = ']';
	/** 换行符, 默认\r\n */
	public String _r = _R;
	
	/** 所有行的信息 */
	public List<LineExp> lines;
	/** 键值对 */
	public Map<String, String> kvMap;
	/** 有序关键字集合 */
	public List<String> kList;
	/** 键值对在 lines中的索引位 */
	public Map<String, Integer> kvInLinesMap;
	
//	/** 所有类型行的信息 */
//	public List<LineExp> glines;
	/** 组集合 */
	public Map<String, LineExp> glMap;
//	/** 类型键值对在 glines中的索引位 */
//	public Map<String, Integer> gInGlinesMap;
	/** 所有的组名 */
	public List<String> gList;
	
	/** 配置文件 */
	public File propFile;
	/** 字符编码, 默认UTF-8 */
	public String charset = "UTF-8";
	/** UTF-8文件的第一个字符为65279 */
	public static final int UTF8_Flg = 65279;
	
	/** 加载字符串 */
	public void load_str(String str) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes()), charset));
		load_br(br);
	}
	
	/** 加载文件 */
	public void load(File file) throws IOException {
		propFile = file;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        load_br(br);
	}

	/** 加载缓存读取器 */
	public void load_br(BufferedReader br) throws IOException {
        String str = null;
        if((str = br.readLine()) != null){
        	LineExp le = null;
            int curLine = 0;	//当前解析到的行号
            boolean isCdata = false;	//true: 代码段
            StringBuilder csb = new StringBuilder();	//
            int _Cdata_S_Len = _Cdata_S.length();
            int _Cdata_E_Len = _Cdata_E.length();
            lines = new ArrayList<LineExp>();
            kvMap = new HashMap<String, String>();
            kList = new ArrayList<String>();
            kvInLinesMap = new HashMap<String, Integer>();
            
//            glines = new ArrayList<LineExp>();
//            gInGlinesMap = new HashMap<String, Integer>();
            glMap = new HashMap<String, LineExp>();
            gList = new ArrayList<String>();
            
            LineExp preLine = null;
    		LineExp preKv = null;
    		LineExp preGroup = null;
        	
        	//去除文件的第一个字符
        	if(curLine == 0 && str.length() > 0 && UTF8_Flg == str.charAt(0)){
        		str = str.substring(1);
        	}
        	// 遍历
	        do{
	        	curLine++;
	        	
	        	//处理代码段结束<![CDATA[abc]]>
	        	if(isCdata){
	        		String str0 = str.trim();
	        		//le = lines.get(lines.size()-1);
	        		if(str0.length() >= _Cdata_E_Len && str0.endsWith(_Cdata_E)){
	        			isCdata = false;
	        			int eix = str.lastIndexOf(_Cdata_E);
	        			//str = str.substring(0, eix);
	        			csb.append(str, 0, eix);
	        			le.val = csb.toString();
	        			kvMap.put(le.key, le.val);
	        		}else{
	        			//str += _r;
	        			csb.append(str).append(_r);
	        		}
//	        		String v = kvMap.get(le.key);
//	        		v += str;
//	        		kvMap.put(le.key, v);
	        		continue;
	        	}
	        	
	        	// 正常键值
	        	le = new LineExp();
	        	str = str.trim();
	        	int slen = str.length();
	        	if(slen == 0){
	        		le.key = "";
	        	}else{
		        	char s_c = str.charAt(0);
		        	char e_c = str.charAt(slen-1);
		        	if(slen < 3){
		        		if(note_char != s_c){
		        			new IOException("第" + curLine + "格式不对! 注释请使用" + note_char + "开始!");
		        		}
		        		le.key = str;
		        	}else{
		        		if(note_char == s_c){
		        			le.key = str;
		        			le.type = Type_Comment;
		        		}else if(type_split_s == s_c && type_split_e == e_c ){
		        			le.key = str.substring(1, slen-1);
		        			le.type = Type_Group;
		        		}else{
		        			// 如果是内容
		        			int ix = str.indexOf(data_split);
		        			if(ix < 1){
		        				new IOException("第" + curLine + "格式不对! 注释请使用" + note_char + "开始!");
		        			}else{
		        				le.type = Type_KV;
		        				le.key = str.substring(0, ix).trim();
		        				String v = "";
		        				if(ix != slen-1){
		        					v = str.substring(ix+1, slen).trim();
		        					//处理代码段开始<![CDATA[abc]]>
		        					if(v.length() >= _Cdata_S_Len && v.startsWith(_Cdata_S)){
		        						le.isCdata = true;
		        						int ve = v.length();
		        						if(v.endsWith(_Cdata_E)){
		        							ve -= _Cdata_E_Len;
		        							v = v.substring(_Cdata_S_Len, ve);
		        							le.val = v;
		        						}else{
		        							isCdata = true;
		        							csb.setLength(0);
		        							csb.append(v, _Cdata_S_Len, v.length()).append(_r);
		        							//v = v.substring(_Cdata_S_Len) + _r;
		        						}
		        					}
		        				}
		        				kvMap.put(le.key, v);
		        				kList.add(le.key);
		        				kvInLinesMap.put(le.key, lines.size());
		        			}
		        		}
		        	}
	        	}
	        	lines.add(le);
	        	le.group = preGroup;
	        	le.preLine = preLine;
	        	if(preLine != null) preLine.nextLine = le;
	        	if(le.type == Type_KV){
	        		le.preKv = preKv;
	        		if(preKv != null) preKv.nextKv = le;
	        		preKv = le;
	        	}else if(le.type == Type_Group){
        			if(preGroup != null) preGroup.nextGroup = le;
        			le.preGroup = preGroup;
        			preGroup = le;
        			
        			//le.group = null;
        			glMap.put(le.key, le);
//        			gInGlinesMap.put(le.key, glines.size());
//        			glines.add(le);
        			gList.add(le.key);
	        	}
	        	preLine = le;
	//        	System.out.println(str); 
	        }while((str = br.readLine()) != null);
        }
        if(br != null) br.close();
    }
	
	public void save(File file) throws IOException{
		saveAs(propFile);
	}
	
	/** 另存为  */
	public void saveAs(File file) throws IOException{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		for(int i = 0, len = lines.size(); i < len; i++){
			LineExp le = lines.get(i);
			if(le.key == null) continue;
			if(le.type == Type_KV){
				String val = kvMap.get(le.key);
				if(val == null) continue;
				bw.append(le.key).append(' ').append(data_split).append(' ');
				if(le.isCdata){
					bw.append(_Cdata_S).append(val).append(_Cdata_E);
				}else{
					bw.append(val);
				}
			}else if(le.type == Type_Group){
				bw.append(type_split_s).append(le.key).append(type_split_e);
			}else{
				bw.append(le.key);
			}
			bw.append(_r);
		}
		if(bw != null) bw.close();
	}
	
	/** 新增或修改一个配置值, val=null则不保存到ini文件 */
	public String put(String key, String val){
		return put(key, val, null);
	}
	
	/** 新增或修改一个配置值, val=null则不保存到ini文件 */
	public String put(String key, String val, String group){
		if(key == null || key.length() == 0) return null;
		key = key.trim();
		if(key.length() == 0) return null;
		String ov = null;
		LineExp gle = glMap.get(group);
		if(gle == null){
			if(kvMap.containsKey(key)){
				ov = kvMap.put(key, val);
				Integer eix = kvInLinesMap.get(key);
				LineExp le = lines.get(eix);
				le.val = val;
			}else{
				ov = kvMap.put(key, val);
				LineExp le = new LineExp();
				le.type = Type_KV;
				le.key = key;
				le.val = val;
				
				//处理上下关系
				int ll = lines.size();
				LineExp preLe = null;
				if(ll - 1 >= 0 && (preLe = lines.get(ll - 1)) != null){
					le.group = preLe.group;
					le.preLine = preLe;
					preLe.nextLine = le;
					
					if(preLe.type == Type_KV){
						le.preKv = preLe;
						preLe.nextKv = le;
					}else{
						if(preLe.preKv != null){
							le.preKv = preLe.preKv;
							preLe.preKv.nextKv = le;
						}
					}
				}
				kvInLinesMap.put(key, ll);
				lines.add(le);
			}
		}
		return ov;
	}
	
	/** 获取配置值 */
	public String get(String key){
		String v = kvMap.get(key);
		return v;
	}
	
	/** 键组 */
	public String[] keyArr(){
		String[] ks = (String[])kvMap.keySet().toArray();
		return ks;
	}
	
	/** 返回 Map&lt;键名, 值> */
	public Map<String, String> toMap(){
		return toMap(true);
	}
	
	/**
	 * 返回 Map&lt;键名, 值>
	 * @param isnew (boolean) true: 克隆一个, false: 返回
	 */
	public Map<String, String> toMap(boolean isnew){
		if(isnew){
			Map<String, String> nmap = new HashMap<String, String>();
			nmap.putAll(kvMap);
			return nmap;
		}
		return kvMap;
	}
	
	/**
	 * 返回 Map&lt;键名, 值>
	 * @param keys (String[]) 指定的key
	 */
	public Map<String, String> toMap(String[] keys){
		Map<String, String> vs = null;
		if(keys != null && keys.length > 0){
			vs = new HashMap<String, String>();
			String key = null;
			for(int i = 0; i < keys.length; i++){
				key = keys[i];
				vs.put(key, kvMap.get(key));
			}
		}
		return vs;
	}
	
	/** 键名集合 */
	public List<String> getKeyList(){
		return kList;
	}
	
	/** 组名集合 */
	public List<String> getGroupNameList(){
		return gList;
	}
	
	/** 获取某组的键值对 */
	public Map<String, String> getKvMapByGroup(String g){
		Map<String, String> map = new HashMap<String, String>();
		LineExp group = null;
		if(g != null && (group = glMap.get(g)) != null){
			LineExp le = null;
			for(int i = 0, len = lines.size(); i < len; i++){
				le = lines.get(i);
				if(le.group == group && le.type == Type_KV){
					map.put(le.key, le.val);
				}
			}
		}
		return map;
	}

	/**
	 * 获取一组配置值
	 * @param keys (List<String>) 指定的key
	 */
	public List<String> gets(List<String> keys){
		List<String> vs = null;
		if(keys != null && keys.size() > 0){
			vs = new ArrayList<String>();
			for(int i = 0; i < keys.size(); i++){
				vs.add(kvMap.get(keys.get(i)));
			}
		}
		return vs;
	}

	/**
	 * 获取一组配置值
	 * keys (String[]) 指定的key
	 */
	public String[] gets(String[] keys){
		String[] vs = null;
		if(keys != null && keys.length > 0){
			vs = new String[keys.length];
			for(int i = 0; i < keys.length; i++){
				vs[i] = kvMap.get(keys[i]);
			}
		}
		return vs;
	}
	
	
	// TODO 其他辅助
	
	/** 输出 */
	public void list(PrintStream out, boolean showComm){
		for(int i = 0, len = lines.size(); i < len; i++){
			LineExp le = lines.get(i);
			if(le.type == Type_KV){
				String val = kvMap.get(le.key);
				out.println(le.key + data_split + val);
			}else if(showComm){
				out.println(le.key);
			}
			
		}
	}
	/** 将值中的双\换成单的, 慎用 */
	public void dropTran(){
		for(int i = 0, len = lines.size(); i < len; i++){
			LineExp le = lines.get(i);
			if(le.type == Type_KV){
				String val = kvMap.get(le.key);
				val = val.replaceAll("\\\\:", ":");
				val = val.replaceAll("\\\\\\\\", "\\\\");
				kvMap.put(le.key, val);
			}else{
				le.key = le.key.replaceAll("\\\\:", ":");
				le.key = le.key.replaceAll("\\\\\\\\", "\\\\");
			}
			
		}
	}
	
	/**
	 * 开启Debug模式, 将key的后缀为debugKeyExt的值设置为主要值如: "ab_Debug_": 12 --> "ab":12
	 * @param debugKeyExt (String) 支持多个, 如: "_Debug_,_LDebug_"
	 */
	public int openDebugKey(String debugKeyExt){
		int c = -1;
		Map<String, String> tmap = new HashMap<String, String>();
		if(debugKeyExt != null && debugKeyExt.length() > 0){
			String[] skes = debugKeyExt.split("\\s*?,\\s*?");
			if(skes == null || skes.length == 0) return -1;
			c = 0;
			
			String dke = null;
			for(int k = 0; k < skes.length; k++){
				dke = skes[k];
				if(dke != null && dke.length() > 0){
					int elen = dke.length();
					for(int i = 0; i < kList.size(); i++){
						String key = kList.get(i);
						if(key != null && key.endsWith(dke)){
							c++;
							String nkey = key.substring(0, key.length() - elen);
							String oval = kvMap.get(nkey);
							kvMap.put(nkey, kvMap.get(key));
							
							// 备份
							String obkey = nkey + Orig_Back_Ext;
							if(!tmap.containsKey(obkey)){
								tmap.put(obkey, oval);
								if(!kvMap.containsKey(obkey)){
									put(obkey, oval);
								}
							}
						}
					}
				}
			}
			
			// 文件Debug模式
			String ofs = kvMap.get("C_OpenDebug_File");
			if(ofs != null && ofs.length() > 0 
					&& (ofs = ofs.trim().replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "")).length() > 0){
				String[] ofsa = ofs.split("\\s*?,\\s*?");
				String ofn = null;
				for(int i = 0; i < ofsa.length; i++){
					ofn = ofsa[i];
					ofn = repFilePath(ofn);
					if(ofn.length() > 0){
						try {
							File file = new File(ofn);
							MIni mini = new MIni();
							mini.load(file);
							int xc = mini.openDebugKey(debugKeyExt);
							if(xc > 0) mini.save(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			return c;
		}else{
			// 还原 备份的原值
			c = 0;
			int elen = Orig_Back_Ext.length();
			for(int i = 0; i < kList.size(); i++){
				String key = kList.get(i);
				if(key != null && key.endsWith(Orig_Back_Ext)){
					c++;
					String nkey = key.substring(0, key.length() - elen);
					kvMap.put(nkey, kvMap.get(key));
				}
			}
			
			// 文件Debug模式
			String ofs = kvMap.get("C_OpenDebug_File");
			if(ofs != null && ofs.length() > 0 
					&& (ofs = ofs.trim().replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "")).length() > 0){
				String[] ofsa = ofs.split("\\s*?,\\s*?");
				String ofn = null;
				for(int i = 0; i < ofsa.length; i++){
					ofn = ofsa[i];
					ofn = repFilePath(ofn);
					if(ofn.length() > 0){
						try {
							File file = new File(ofn);
							MIni mini = new MIni();
							mini.load(file);
							int xc = mini.openDebugKey(debugKeyExt);
							if(xc > 0) mini.save(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return c;
	}
	
	/** 行表示 */
	public class LineExp{
		/** 类型, Type_Comment:注释(默认), Type_KV:键值对  */
		public int type = Type_Comment;
		/** true: 代码段  */
		public boolean isCdata = false;
		/** 如果是注释, 则为注释的内容; 如果是键值对, 则是键 */
		public String key = "";
		public String val = null;
		
		public LineExp group;
		
		public LineExp preLine;
		public LineExp nextLine;
		public LineExp preKv;
		public LineExp nextKv;
		public LineExp preGroup;
		public LineExp nextGroup;
		
	}
	
	// TODO 配置
	
	/**
	 * 配置符号, null:则保存默认
	 * @param note_char (Character) 注释前缀符, 默认#
	 * @param data_split (Character) 数据分割符, 默认=
	 * @param type_split_s (Character) 类型开始符, 默认[
	 * @param type_split_e (Character) 类型结束符, 默认]
	 * @param _r (Character) 换行符, 默认\r\n
	 * @param charset (Character) 字符编码, 默认UTF-8
	 */
	public void cfg_sign(Character note_char, Character data_split, Character type_split_s, Character type_split_e, String _r, String charset){
		if(note_char != null) this.note_char = note_char;
		if(data_split != null) this.data_split = data_split;
		if(type_split_s != null) this.type_split_s = type_split_s;
		if(type_split_e != null) this.type_split_e = type_split_e;
		if(_r != null) this._r = _r;
		if(charset != null) this.charset = charset;
	}
	
	// TODO 键值对翻转
	
	/**
	 * 将键值对翻转, 返回一个新的MIni
	 * @param mi 
	 * @return
	 */
	public static MIni reKv(MIni mi){
		MIni ni = new MIni();
		
		return ni;
	}
	
	/** 预处理文件路径 */
	public String repFilePath(String fn){
		if(fn.startsWith("classpath:")){
			fn = fn.substring(10);
			fn = getRealPath(fn);
		}
		return fn;
	}
	
	/** 获取类根路径 */
	public static String getClassRoot(){
		String path = MIni.class.getResource("/").getPath();
		if(path != null && path.length() > 1){
			if(path.indexOf(':') > 0){
				return path.substring(1);
			}
			return path;
		}
		return "/";
	}
	
	/** 获取真实的路径 */
	public static String getRealPath(String path){
		if(path.charAt(0) == '/'){
			path = getClassRoot() + path.substring(1);
		}else if(path.charAt(1) != ':'){
			path = getClassRoot() + path;
		}
		return path;
	}
	
	public static void main(String[] args) {
		MIni prop = new MIni();
		File file = new File("E:\\shanhongWeb\\HljRatData\\test\\path.ini");
		try {
			prop.load(file);
			prop.list(System.out, true);
			//prop.dropTran();
			prop.saveAs(new File(file.toString()+"2"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
