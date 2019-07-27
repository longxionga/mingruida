
function getMap() {//初始化map_,给map_对象增加方法，使map_像Map

	var map_ = new Object();

	map_.put = function(key, value) {

	map_[key+'_'] = value;

	};

	map_.get = function(key) {

	return map_[key+'_'];

	};

	map_.remove = function(key) {

	delete map_[key+'_'];

	};

	map_.keyset = function() {

	var ret = "";

	for(var p in map_) {

	if(typeof p == 'string' && p.substring(p.length-1) == "_") {

	ret += ",";

	ret += p.substring(0,p.length-1);

	}

	}

	if(ret == "") {

	return ret.split(",");

	} else {

	return ret.substring(1).split(",");

	}

	};

	return map_;

	}
//初始化字典表数据
var m = getMap();
function dict(dict_code){
	$.ajax({
        type: "POST",
        url: '../sys/getDict?dict_code='+dict_code,
        data: {
        	dict_code:dict_code
        }, 
        dataType: 'json',
        async: false,
        success: function(data){        	
     	for(var i=0;i<data.length;i++){
     		m.put(data[i].dict_id,data[i].dict_name);
     	}         	
        }
     });
}
//下拉菜单字典
function dictInfo(dict_code,controlName){	
	$('#'+controlName).combobox({
		url : '../sys/getDict?dict_code='+dict_code,
		valueField : 'dict_id',
		textField : 'dict_name',
		width : '150',
		height : '30',
		editable : false,
		panelHeight : 'auto'
	});
}

//下拉菜单字典
function dictInfos(dict_code,controlName){
    $('#'+controlName).combobox({
        url : '../sys/getDict?dict_code='+dict_code,
        valueField : 'dict_id',
        textField : 'dict_cname',
        width : '150',
        height : '30',
        editable : false,
        panelHeight : '200'
    });
}

//下拉菜单字典
function dictInfoValue(dict_code,controlName){
    $('#'+controlName).combobox({
        url : '../sys/getDict?dict_code='+dict_code,
        valueField : 'dict_value',
        textField : 'dict_cname',
        width : '150',
        height : '30',
        editable : false,
        panelHeight : '200'
    });
}

//下拉菜单字典
function dictInfoValues(dict_code,controlName){
    $('#'+controlName).combobox({
        url : '../sys/getDict?dict_code='+dict_code,
        valueField : 'dict_value',
        textField : 'dict_name',
        width : '150',
        height : '30',
        editable : false,
        panelHeight : '200'
    });
}


/**
 * 字典格式化 
 * @param value
 * @returns {String}
 */
function dictFormatter(value) {	
	return m.get(value);
};