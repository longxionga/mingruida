(function ($) {
	function createBox(me, options) {
		me.html('');

		if(options.data){
			$.each(options.data,function(index,item){
				me.append('<input type="checkbox" value="'+item.value+'"/>'+item.text);
			});
			registerEvent(me);
		}
	}

	function registerEvent(me){
		$(me).children().on('click',function(){
			if($(this).attr('checked')){
				$(this).removeAttr('checked');
			}else{
				$(this).attr('checked','checked');
			}
		});
	}

	$.fn.checkbox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.checkbox.methods[options];

			if (method){
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}

		options = options || {};
		createBox(this,options);
	};

	$.fn.checkbox.methods={
		setValue:function(me,para){
			me.html('');

			createBox(me,{data:para});
		},
		getValue:function(me,para){
			var values=new Array();

			$(me).children().each(function(index,item){
				if($(item).attr('checked')=='checked'){
					values.push($(item).attr('value'));
				}
			});

			return values;
		},
		checked:function(me,para){
			$(me).children().each(function(index,item){
				if (para.indexOf($(item).attr('value')) > -1) {
					if ($(item).attr('checked') != 'checked') {
						$(item).click();
					}
				} else {
					$(item).removeAttr('checked');
				}
			});
		}
	};
})(jQuery);