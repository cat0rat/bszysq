$(function(){
	window.isIndexJsp = true;
	
	$(".a_ac_tab").click(M.tabs.toCenter);	//监听,点击菜单,在Tab页中显示
	
	var layout = $("#mainLayout");
    setTimeout(function () {
        layout.removeClass("hidden").layout("resize");
        $("#maskContainer").remove();
    },0);
	

	//初始化主页的Tabs
	var tabs = $('#i_tabs');
	tabs.tabs({   
		fit:true,
	    border:false,
	    /** 当Tab关闭时, 调用其iframe中的onPtTabBeforeClose函数 */
	    onBeforeClose:function(title, index){
	    	var tabs = $(this);
	    	var ifr = M.tabs.getIframe(tabs, title);
	    	var is_cl = true;
	    	if(ifr){
	    		try{
		    		var w = ifr[0].window || ifr[0].contentWindow;
		    		if(w && w.onPtTabBeforeClose){
		    			var cl = w.onPtTabBeforeClose.call(w, window, tabs, title);
		    			is_cl = cl !== false;
		    		}
	    		}catch(e){};
	    	}
	    	if(is_cl){
		    	setTimeout(function(){
		    		M.tabs.forceCloseTab(tabs, title);
		    	}, 100);
	    	}
	    	return false;
	    },
	    onSelect:function(title){   
	        
	    }   
	});  
	
	M.TabRMenu.init({
		tabsId: 'i_tabs'
	});
	
	
	//-- 登出
    

});
