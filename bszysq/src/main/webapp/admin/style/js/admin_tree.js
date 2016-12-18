function build_tree_art(cates, topics){
	var nav_tree = {
		art:[{
			text: '版块管理',
			beans: {
				href:'/admin/category/'
			}
		},{
			text: '标签管理',
			beans: {
				href:'/admin/arttag/'
			}
		},{
			text: '主题管理',
			beans: {
				href:'/admin/article/'
			}
//		},{
//			text: '评论管理',
//			beans: {
//				href:'/admin/comment/page.do'
//			}
		},{
			text: '轮播管理',
			beans: {
				href:'/admin/slider/'
			}
		},{
			text: '推送管理',
			beans: {
				href:'/admin/sysmsg/'
			}
		}],
		sys:[{
			text: '用户管理',
			beans: {
				href:'/admin/user/'
			}
//		},{
//			text: '短信验证码管理',
//			beans: {
//				href:'/admin/smscode/'
//			}
//			},{
//				text: '修改密码',
//				beans: {
//					href:''
//				}
		},{
			text: '退出',
			beans: {
				href:'',
				_fn: 'logout'
			}
		}]
	};
	return nav_tree;
}

// 构建 子主题管理树, 需要 jar.cates 和 jar topics
function subtopic_tree_build(cates, topics){
	cates = cates || m_i.ssub_val('jar.map.cates.rows');
	topics = topics || m_i.ssub_val('jar.map.topics.rows');
	var cds = [];
	var cos = {};
	if(cates && cates.length){
		var cate, co;
		for(var i = 0; i < cates.length; i++){
			cate = cates[i];
			co = {
				text: cate.name,
				beans: cate
			};
			cds.push(co);
			cos[cate.id] = co;
		}
	}
	if(topics && topics.length){
		var topic, to, cot;
		for(var k = 0; k < topics.length; k++){
			topic = topics[k];
			cot = cos[topic.cid];
			if(cot){
				if(!cot.children) cot.children = [];
				topic.stid = topic.id;
				to = {
					text: topic.name,
					beans: topic
				};
				cot.children.push(to);
			}
		}
	}
	admin_tree_subtopic = cds;
	return cds;
}

// 重新加载 子主题管理 树
function reload_nav_tree_art(){
	$.ajax({
		url : "/admin/sys/cates_topics.json",
		//data : ps,
		success : function(d) {
			if(d.success){
				if(d.map){
					var cates = m_i.ssub_val(d.map, 'cates.rows');
					var topics = m_i.ssub_val(d.map, 'topics.rows');
					var nav_tree = build_tree_art(cates, topics);
					var at = $('#art-tree');
					at.tree('loadData', nav_tree.art);
				}
			}else{
				M.err('刷新左侧菜单失败');
			}
		}
	});
}

$(function(){
	var nav_tree = build_tree_art();
	// 文章管理
	var at = $('#art-tree');
	at.tree({
		data: nav_tree.art,
		lines: true,
		onClick: onClick
	});
	
	// 系统管理
	var st = $('#sys-tree');
	st.tree({
		data: nav_tree.sys,
		lines: true,
		onClick: onClick
	});
	
	function onClick(node){
		var bs = node.beans;
		if(!bs || bs.ig_clk) return ;
		
		var _fn = fns[bs._fn];
		_fn && _fn();
		
		var is_go = 0;
		var href = '';
		var title = '';
		if(bs.stid){
			is_go = 1;
			title = node.text;
			href = '/admin/art/subtopic/' + bs.stid;
		}else if(bs.href){
			is_go = 1;
			title = node.text;
			href = bs.href;
		}
		
		if(is_go){
			M.tabs.toCenter({
				url: href,
				title: title,
				closeOld: 1
			});
		}
	}
	
	var fns = {
		logout: function(){
			if(confirm('您确定要退出吗？')){ 
				return window.location = '/admin/logout';
			};
		}
	};
	
});
