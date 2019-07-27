(function ($) {

	function init(target){
		// 用隐藏控件提交选中/非选中的值
		var el = $('<input type="hidden" />');
		el.insertAfter(target);

		var name = $(target).attr('name');
		if (name){
			el.attr('name', name);
			$(target).removeAttr('name').attr('checkboxName', name);
		}

		return el;
	}

	function destroy(target){
		var el = $.data(target, 'checkbox').valueEl;
		if (el) {el.remove();}

		$(target).remove();
	}

	// 根据checkbox的勾选状态, 更新hidden控件的值
	function updateValue(target){
		var state = $.data(target, 'checkbox');

		var data = state.options.data;
		var value = (target.checked?data.value:data.uncheckedValue);
		var text = (target.checked?data.text:data.uncheckedText);

		var el = state.valueEl;
		el.val(value);
		el.attr("text", text);
	}

	function getValue(target){
		var el = $.data(target, 'checkbox').valueEl;
		return el.val();
	}

	function getText(target){
		var el = $.data(target, 'checkbox').valueEl;
		return el.attr("text");
	}

	function setDisabled(target, disabled){
		var state = $.data(target, 'checkbox');
		if (!state) {
			// 有可能在控件初始化前先调用了本方法
			target.disabled = disabled;
			return;
		}

		var opts = state.options;
		if (disabled){
			opts.disabled = true;
			$(target).attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
		}
	}

	// 勾选时更新值
	function bindEvents(target){
		$(target).unbind(".checkbox");

		$(target).bind("change.checkbox",function(){
			updateValue(this);
		});
	}

	// 解析 html 的内容
	function parseOptions(target){
		return $.extend({}, $.parser.parseOptions(target));
	};

	// 加载数据
	// 格式: {'value':'1', 'text':'男', 'uncheckedValue':'0', 'uncheckedText':'女'}
	function loadData(target, data){
		var opts = $.data(target, 'checkbox').options;
		opts.data = data;

		updateValue(target);
	}

	$.fn.checkbox = function (options, param) {
		if (typeof options == 'string') {
			return $.fn.checkbox.methods[options](this, param);
		}

		options = options || {};
		return this.each(function () {
			var state = $.data(this, 'checkbox');
			if (!state) {
				var opts = $.extend({}, $.fn.checkbox.defaults, parseOptions(this), options);
				var el = init(this);

				state = {
					options: opts,
					data: [],
					valueEl: el
				};
				$.data(this, 'checkbox', state);

			} else {
				$.extend(state.options, options);
			}

			if (state.options.data){
				loadData(this, state.options.data);
			}

			setDisabled(this, state.options.disabled);
			bindEvents(this);
		});
	};


	$.fn.checkbox.methods = {
		options: function(jq){
			return $.data(jq[0], 'checkbox').options;
		},
		readonly: function(jq, readonly){
			//if (readonly == undefined) readonly = true;

			jq.each(function(){
				setDisabled(this, readonly);
			});
		},

		getText: function(jq){
			return getText(jq[0]);
		},

		getValue: function(jq){
			return getValue(jq[0]);
		}

	};

	$.fn.checkbox.defaults = {
		disabled: false
	};

})(jQuery);