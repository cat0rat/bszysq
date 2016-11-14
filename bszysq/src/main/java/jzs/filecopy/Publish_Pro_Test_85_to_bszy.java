package jzs.filecopy;

import com.mao.ini.PathIniUtil;
import com.mao.packpro.Publish_Pro_Base;

/**
 * 一键发布项目(测试服务器(锦绣))
 * @author Mao 2016年4月26日 下午3:26:03
 */
public class Publish_Pro_Test_85_to_bszy extends Publish_Pro_Base {
	
	public static void main(String[] args) throws Exception {
		// 开启(测试模式)
		PathIniUtil.openDebug();
		
		try {
			Publish_Pro_Test_85_to_bszy pub = new Publish_Pro_Test_85_to_bszy();
			
			// pub.tomcat_port  = 23900;
			pub.remote_dir = "/home/bjjx/tomcat-jzs_xw/xtmp";
			pub.root_pw = "jx2016ABCD";
			pub.build_sh = "./jzs_xw_build.sh";
			pub.ck_key = "\\/tomcat-jzs_xw\\/";
			pub.pro_zip_path = "/temp/bszy-85/ROOT.zip";
			pub.cmpr_filter_Dir = new String[]{
				"ROOT\\WEB-INF\\lib",
//				"ROOT\\js",
			};
			
//			pub.pack();	
//			pub.upload();
			pub.publish();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 恢复(本地测试模式)
		PathIniUtil.openLDebug();
	}
	
}
