$(function () {
    $(".js-detail").click(function () {
        $(this).parent().find(".detail-layer-tips").css("display", "block");
    });

    $(".js-close").click(function () {
        $(this).parent().css("display", "none");
    });

    function init() {

        var _nTimer = 0,
            _oGuide$ = $("#guide"),
            _oGuideTrigger$ = $("#QRcode");

        function _back() {
            _nTimer = setTimeout(function () {
                _oGuide$.stop().animate({ marginLeft: "-101px", opacity: 0 }, "400", "swing", function () {
                    _oGuide$.hide();
                });
            }, 100);
        }

        /*guide*/
        _oGuide$.css({ "left": "50%", "opacity": 0 });
        _oGuideTrigger$.mouseover(function () {
            clearTimeout(_nTimer);
            _oGuide$.css("display", "block").stop().animate({ marginLeft: "+147px", opacity: 1 }, 900, "swing", function () {
                _oGuide$.animate({ marginLeft: "+134px" }, 300);
            });
        }).mouseout(_back);

        _oGuide$.mouseover(function () {
            clearTimeout(_nTimer);
        }).mouseout(_back);
    }

    init();
});

//function _poll() {
//    var self = arguments.callee,
//            pollUUID = setting.uuid,
//            repoll = function (pollUUID) {
//                if (pollUUID != setting.uuid) return;
//                setTimeout(self, 1000);
//            },
//            succCallback = function (code) {
//                //code = 202;
//                switch (code) {
//                    case 408: //扫描未知
//                    case 200:
//                        repoll(pollUUID);
//                        break;
//                    case 203: //扫描成功                       }
//                        $("#qr_normal .msg_default_cg").css("display", "none");
//                        $("#qr_normal .msg_default_box").removeAttr("style");
//                        repoll(pollUUID);
//                        break;
//                    case 205: //扫描成功——取消
//                        $("#qr_normal .msg_default_cg").removeAttr("style");
//                        $("#qr_normal .msg_default_box").css("display", "none");
//                        repoll(pollUUID);
//                        break;
//                    case 204: //支付未知
//                        repoll(pollUUID);
//                        break;
//                    case 201: //支付成功
//                        $("#payMsg").children("div").css("display", "none");
//                        $("#pay_succ").removeAttr("style");
//                        var count = parseInt($("#redirectTimer").text());
//                        var interval = setInterval(function () {
//                            $("#redirectTimer").text(--count);
//                            if (count == 0) {
//                                location.href = setting.url + "?wechat=" + setting.code + "&orderid=" + setting.orderid + "&orderno=" + setting.orderno + "&total_fee=" + setting.total_fee;
//                                clearInterval(interval);
//                            }
//                        }, 1000);
//                        break;
//                    case 202: //支付失败
//                        $("#payMsg").children("div").css("display", "none");
//                        $("#pay_error").removeAttr("style");
//                        break;
//                    case 400: //uuid失效
//                        break;
//                    default:
//                        repoll(pollUUID);
//                }
//            };
//    window.debug = succCallback;
//    $.ajax({
//        url: "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login",
//        type: "POST",
//        dataType: "script",
//        data: {
//            uuid: pollUUID,
//            tip: 1,
//            _: new Date().getTime(),
//            code: window.code
//        },
//        cache: false,
//        timeout: 33 * 1000, //后台是30s
//        success: function () {
//            if (!window.code) {

//                repoll(pollUUID);
//                return;
//            }
//            succCallback(window.code);
//        },
//        error: function () {
//            repoll(pollUUID);
//        }
//    });
//}
//_poll();