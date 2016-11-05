function getPos(ele) {
	var position = new Object();
	position.x = pos_getPageOffsetLeft(ele);
	position.y = pos_getPageOffsetTop(ele);
	position.w = ele.offsetWidth;
	position.h = ele.offsetHeight;
	return position;
}

function pos_getPageOffsetLeft(ele){
	var retLeft = ele.offsetLeft;
	while((ele = ele.offsetParent) != null)
	{
		retLeft += ele.offsetLeft;
	}
	return retLeft;
}

function pos_getPageOffsetTop(ele){
	var retTop = ele.offsetTop;
	while((ele = ele.offsetParent) != null)
	{
		retTop += ele.offsetTop;
	}
	return retTop;
}