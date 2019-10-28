var OOP = OOP || {};
(function() {
	var global = window;
	/**
	 * 
	 * @param {}
	 *            nsStr
	 * @return {}
	 */
	OOP.ns = function(nsStr) {
		var parts = nsStr.split("."), root = global, max, i;
		for (i = 0, max = parts.length; i < max; i++) {
			// 如果不存在，就创建一个属性
			if (typeof root[parts[i]] === "undefined") {
				root[parts[i]] = {};
			}
			root = root[parts[i]];
		}
		return root;
	};
})();