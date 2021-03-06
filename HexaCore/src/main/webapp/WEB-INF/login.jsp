<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HEXACORE</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/css_login.css">
<style type="text/css">
body, html {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
  font-family: sans-serif;
}

canvas {
  background-color: #262626;
}

.credits {
  position: fixed;
  font-size: 1em;
  opacity: 0.9;
  z-index: 1;
  font-size: 0.35em;
  letter-spacing: 2px;
  color: #fff;
}

</style>

</head>
<body>
<div class="credits">
	<!-- 	<img alt="logo" src="./image/hexa512.png"> -->
	<!-- 	<h1>Hexa-core Groupware</h1> -->
	<!-- 	<div id="login"> -->
	<!-- 		<form action="./logingo.do" method="post"> -->
	<!-- 			<input class="id" type="text" name="username" placeholder="USERNAME"><br> -->
	<!-- 			<input class="pw" type="password" name="password" placeholder="PASSWORD"><br><br> -->
	<!-- 			<input class="btn" type="submit" value="Log In"><br><br> -->
	<!-- 			<p><input class="chkbox" name ="remember-me" type = "checkbox"> 로그인 유지</p> -->
	<!-- 		</form> -->
	<!-- 	</div> -->
	<div class="login-box">
		<h2>Login</h2>
		<form action="./logingo.do" method="post">
			<div class="user-box">
				<input type="text" name="username" required="required"> <label>Username</label>
			</div>
			<div class="user-box">
				<input type="password" name="password" required="required"> <label>Password</label>
			</div>
			<a> <span></span> <span></span> <span></span> <span></span>
				<input id="hisub" type="submit" value="LOGIN">
			</a>
			<p><input class="chkbox" name ="remember-me" type = "checkbox"> Remember me</p>
		</form>
	</div>
	<canvas id="c"></canvas>
</div>  
	<script type="text/javascript" defer="defer">
	$(document).ready(function() {
		<c:if test="${not empty msg}">
			alert("${msg}");
		</c:if>
	});
</script>
<script type="text/javascript">
(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){"use strict";var _createClass=function(){function defineProperties(target,props){for(var i=0;i<props.length;i++){var descriptor=props[i];descriptor.enumerable=descriptor.enumerable||false;descriptor.configurable=true;if("value"in descriptor)descriptor.writable=true;Object.defineProperty(target,descriptor.key,descriptor)}}return function(Constructor,protoProps,staticProps){if(protoProps)defineProperties(Constructor.prototype,protoProps);if(staticProps)defineProperties(Constructor,staticProps);return Constructor}}();Object.defineProperty(exports,"__esModule",{value:true});var _Hexagon=require("./objects/Hexagon");var _Hexagon2=_interopRequireDefault(_Hexagon);function _interopRequireDefault(obj){return obj&&obj.__esModule?obj:{"default":obj}}function _classCallCheck(instance,Constructor){if(!(instance instanceof Constructor)){throw new TypeError("Cannot call a class as a function")}}var Scene=function(){function Scene($canvas,width,height){_classCallCheck(this,Scene);this.$canvas=$canvas;this.context=this.$canvas.getContext("2d");this.width=0;this.height=0;this.params={};this.tick=0;this.lastTime=0;this.hexagons=[];var h=undefined;for(var i=0;i<8;i++){h=new _Hexagon2.default(width,height,1-i/9);h.alpha=.5-i*.5/8;this.hexagons.push(h)}this.nbHexagon=this.hexagons.length;this.resize(width,height)}_createClass(Scene,[{key:"resize",value:function resize(width,height){this.width=width;this.height=height;this.hexagons.forEach(function(hexa){hexa.resize(width,height)});this.$canvas.width=width;this.$canvas.height=height}},{key:"render",value:function render(){var _this=this;var now=Date.now();var elapsed=(now-this.lastTime)/1e3;this.tick+=elapsed;this.context.clearRect(0,0,this.width,this.height);this.hexagons.forEach(function(hexa,i){if(i===~~(_this.tick*10%_this.nbHexagon)){hexa.alpha=1}else{hexa.alpha=.5-i*.5/8}hexa.update(_this.context,_this.tick)});this.lastTime=now}}]);return Scene}();exports.default=Scene},{"./objects/Hexagon":3}],2:[function(require,module,exports){"use strict";var _Scene=require("./Scene");var _Scene2=_interopRequireDefault(_Scene);var _raf=require("raf");var _raf2=_interopRequireDefault(_raf);function _interopRequireDefault(obj){return obj&&obj.__esModule?obj:{"default":obj}}var canvas=document.getElementById("c");var scene=new _Scene2.default(canvas,window.innerWidth,window.innerHeight);window.addEventListener("resize",resizeHandler);animate();function resizeHandler(){scene.resize(window.innerWidth,window.innerHeight)}function animate(){(0,_raf2.default)(animate);scene.render()}},{"./Scene":1,raf:6}],3:[function(require,module,exports){"use strict";var _createClass=function(){function defineProperties(target,props){for(var i=0;i<props.length;i++){var descriptor=props[i];descriptor.enumerable=descriptor.enumerable||false;descriptor.configurable=true;if("value"in descriptor)descriptor.writable=true;Object.defineProperty(target,descriptor.key,descriptor)}}return function(Constructor,protoProps,staticProps){if(protoProps)defineProperties(Constructor.prototype,protoProps);if(staticProps)defineProperties(Constructor,staticProps);return Constructor}}();Object.defineProperty(exports,"__esModule",{value:true});function _classCallCheck(instance,Constructor){if(!(instance instanceof Constructor)){throw new TypeError("Cannot call a class as a function")}}var Hexagon=function(){function Hexagon(wWidth,wHeight,scale){_classCallCheck(this,Hexagon);this.width=1700*scale;this.height=1650*scale;this.x=.5*wWidth;this.y=.5*wHeight;this.scale=1;this.alpha=1}_createClass(Hexagon,[{key:"resize",value:function resize(wWidth,wHeight){this.x=.5*wWidth;this.y=.5*wHeight}},{key:"draw",value:function draw(context){context.lineWidth=6;context.lineJoin="round";context.strokeStyle="#fff";context.moveTo(this.x-.25*this.width,this.y-.5*this.height);context.beginPath();context.lineTo(this.x+.25*this.width,this.y-.5*this.height);context.lineTo(this.x+.5*this.width,this.y);context.lineTo(this.x+.25*this.width,this.y+.5*this.height);context.lineTo(this.x-.25*this.width,this.y+.5*this.height);context.lineTo(this.x-.5*this.width,this.y);context.lineTo(this.x-.25*this.width,this.y-.5*this.height);context.closePath();context.stroke()}},{key:"update",value:function update(context,time){context.save();context.globalAlpha=this.alpha;this.draw(context);context.restore()}}]);return Hexagon}();exports.default=Hexagon},{}],4:[function(require,module,exports){(function(process){(function(){var getNanoSeconds,hrtime,loadTime;if(typeof performance!=="undefined"&&performance!==null&&performance.now){module.exports=function(){return performance.now()}}else if(typeof process!=="undefined"&&process!==null&&process.hrtime){module.exports=function(){return(getNanoSeconds()-loadTime)/1e6};hrtime=process.hrtime;getNanoSeconds=function(){var hr;hr=hrtime();return hr[0]*1e9+hr[1]};loadTime=getNanoSeconds()}else if(Date.now){module.exports=function(){return Date.now()-loadTime};loadTime=Date.now()}else{module.exports=function(){return(new Date).getTime()-loadTime};loadTime=(new Date).getTime()}}).call(this)}).call(this,require("_process"))},{_process:5}],5:[function(require,module,exports){var process=module.exports={};var queue=[];var draining=false;var currentQueue;var queueIndex=-1;function cleanUpNextTick(){draining=false;if(currentQueue.length){queue=currentQueue.concat(queue)}else{queueIndex=-1}if(queue.length){drainQueue()}}function drainQueue(){if(draining){return}var timeout=setTimeout(cleanUpNextTick);draining=true;var len=queue.length;while(len){currentQueue=queue;queue=[];while(++queueIndex<len){if(currentQueue){currentQueue[queueIndex].run()}}queueIndex=-1;len=queue.length}currentQueue=null;draining=false;clearTimeout(timeout)}process.nextTick=function(fun){var args=new Array(arguments.length-1);if(arguments.length>1){for(var i=1;i<arguments.length;i++){args[i-1]=arguments[i]}}queue.push(new Item(fun,args));if(queue.length===1&&!draining){setTimeout(drainQueue,0)}};function Item(fun,array){this.fun=fun;this.array=array}Item.prototype.run=function(){this.fun.apply(null,this.array)};process.title="browser";process.browser=true;process.env={};process.argv=[];process.version="";process.versions={};function noop(){}process.on=noop;process.addListener=noop;process.once=noop;process.off=noop;process.removeListener=noop;process.removeAllListeners=noop;process.emit=noop;process.binding=function(name){throw new Error("process.binding is not supported")};process.cwd=function(){return"/"};process.chdir=function(dir){throw new Error("process.chdir is not supported")};process.umask=function(){return 0}},{}],6:[function(require,module,exports){var now=require("performance-now"),global=typeof window==="undefined"?{}:window,vendors=["moz","webkit"],suffix="AnimationFrame",raf=global["request"+suffix],caf=global["cancel"+suffix]||global["cancelRequest"+suffix];for(var i=0;i<vendors.length&&!raf;i++){raf=global[vendors[i]+"Request"+suffix];caf=global[vendors[i]+"Cancel"+suffix]||global[vendors[i]+"CancelRequest"+suffix]}if(!raf||!caf){var last=0,id=0,queue=[],frameDuration=1e2/60;raf=function(callback){if(queue.length===0){var _now=now(),next=Math.max(0,frameDuration-(_now-last));last=next+_now;setTimeout(function(){var cp=queue.slice(0);queue.length=0;for(var i=0;i<cp.length;i++){if(!cp[i].cancelled){try{cp[i].callback(last)}catch(e){setTimeout(function(){throw e},0)}}}},Math.round(next))}queue.push({handle:++id,callback:callback,cancelled:false});return id};caf=function(handle){for(var i=0;i<queue.length;i++){if(queue[i].handle===handle){queue[i].cancelled=true}}}}module.exports=function(fn){return raf.call(global,fn)};module.exports.cancel=function(){caf.apply(global,arguments)}},{"performance-now":4}]},{},[2]);</script>
</body>
</html>