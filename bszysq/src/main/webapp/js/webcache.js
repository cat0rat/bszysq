var webcache={
		setData:function(storekey,value){
			sessionStorage.setItem(storekey, value);
		},
		
		getData:function(storekey){
			var value=sessionStorage.getItem(storekey);
//			var value=undefined;
//			if(storeinfo){
//				var jsonObj=JSON.parse(storeinfo);
//				value=jsonObj[attrkey]
//			}
			return value;
		},
		
		clearData:function(storekey){
			sessionStorage.removeItem(storekey);
		}
}