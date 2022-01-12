var router = new VueRouter(
	{
		mode: 'history'
	}
);
var main =
	new Vue({
		el: '#main',
		router: router,
		data: {
			list: []
		},

		methods: {
			initData: function() {
				const vm = this;

			},
		},

		mounted: function() {
			const el = $(this.$el);
			const vm = this;
			vm.initData();
			el.show();
		}
	});