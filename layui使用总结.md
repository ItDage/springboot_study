##layui使用技巧总结
###外部调用layui内部声明的方法
	layui.use(['element', 'layer'], function() {
		var $ = layui.jquery, element = layui.element, layer = layui.layer; //Tab的切换功能，切换事件监听等，需要依赖element模块
		//触发事件
		$('.site-demo-active').on('click', function() {
			var othis = $(this), type = othis.data('type');
			active[type] ? active[type].call(this, othis) : '';
		});
		//声明这样外部就可以使用test2()
		window.test2 = function(){
			layer.open({
			      type: 2,
			      title: '很多时候，我们想最大化看，比如像这个页面。',
			      shadeClose: true,
			      shade: false,
			      maxmin: true, //开启最大化最小化按钮
			      area: ['893px', '600px'],
			      content: '//fly.layui.com/'
			    });
		}
	});