package com.shyche;

public class shycheCfg {

    /*因第三方配置时常不能访问,所以将配置放在github上.但由于普通设备几乎无法访问github,所以使用代理访问;
    代理也可能挂掉...所以列出多个代理
    使用各个代理去访问各个链接,如果访问失败,就切下一个代理.一旦成功即终止
    */

    String[] proxyUrl = {"https://ghp.ci",
            "https://mirror.ghproxy.com",
            "https://github.moeyy.xyz",
            "https://gitdl.cn",
            "https://ghp.ci",
            "https://ghproxy.net"
    };

    String[] APIGithubUrl = {"https://raw.githubusercontent.com/shyche/ftybendi/main/fty3/fty.json",
            "https://raw.githubusercontent.com/xyq254245/xyqonlinerule/main/XYQTVBox.json", //香雅晴线路
            "https://raw.githubusercontent.com/gaotianliuyun/gao/master/js.json",   //高天流云线路
            "https://raw.githubusercontent.com/PizazzGY/TVBox/main/api.json",       //潇洒线路
            "https://raw.githubusercontent.com/owen2000wy/owentv/main/OwenTV.json"

    };

    String[] APIUrl = {
            "http://肥猫.live",
            "http://肥猫.com",
            "http://pandown.pro/tvbox/tvbox.json",  //巧儿
            "http://ok321.top/ok",
            "https://yydf.540734621.xyz/QQ/yydf2024.json",      //业余打发线路
            "https://szyyds.cn/tv/x.json",   //小马线路
            "https://盒子迷.top/禁止贩卖",
            "https://xn--tkh-mf3g9f.v.nxog.top/m/111.php?ou=公众号欧歌app&mz=index&jar=index&123&b=欧歌tkh",    //欧哥
            "http://cdn.qiaoji8.com/tvbox.json",    //巧记线路
    };


    //所有的live都加载
    String[] liveGithubUrl = {"https://raw.githubusercontent.com/shyche/Live/shyche/IPTV.m3u",
            "https://raw.githubusercontent.com/yuanzl77/IPTV/main/live.m3u",
            "https://raw.githubusercontent.com/YueChan/Live/main/IPTV.m3u",
            "https://raw.githubusercontent.com/Kimentanm/aptv/master/m3u/iptv.m3u"
    };

    String getFirstLiveWithProxy() {
        String url = "";
        String liveUrl = liveGithubUrl[0];
        String proxy = proxyUrl[0];
        if (!proxy.endsWith("/")){
            proxy = proxy+"/";
        }
        url = proxy+liveUrl;

        return url;
    }

//    String getGeneralAPI(){
//
//    }
}
