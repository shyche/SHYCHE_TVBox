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
            "https://ghproxy.net",
            "https://ghproxy.cn",
            "https://gh.llkk.cc",
            "https://gh-proxy.llyke.com",
            "https://www.ghproxy.cc",
            "https://cf.ghproxy.cc"
    };

    String[] APIGithubUrl = {"https://raw.githubusercontent.com/shyche/ftybendi/main/fty3/fty.json",
            "https://raw.githubusercontent.com/xyq254245/xyqonlinerule/main/XYQTVBox.json", //香雅晴线路
            "https://raw.githubusercontent.com/gaotianliuyun/gao/master/js.json",   //高天流云线路
            "https://raw.githubusercontent.com/PizazzGY/TVBox/main/api.json",       //潇洒线路
            "https://raw.githubusercontent.com/owen2000wy/owentv/main/OwenTV.json",
            "https://raw.githubusercontent.com/yuanzl77/TVBox-url/main/tv.txt"  //yuanzl77.github.io

    };

    String[] APIUrl = {
            "http://肥猫.live",
            "http://肥猫.com",
            "http://pandown.pro/tvbox/tvbox.json",  //巧儿
            "http://ok321.top/ok",      //需要关注公众号...
            "https://yydf.540734621.xyz/QQ/yydf2024.json",      //业余打发线路
            "https://szyyds.cn/tv/x.json",   //小马线路
            "https://盒子迷.top/禁止贩卖",
            "https://xn--tkh-mf3g9f.v.nxog.top/m/111.php?ou=公众号欧歌app&mz=index&jar=index&123&b=欧歌tkh",    //欧哥
            "http://cdn.qiaoji8.com/tvbox.json",    //巧姐线路
            "http://175.178.251.183:6689/tv.txt"       //yuanzl77.github.io

    };


    //所有的live都加载
    String[] liveGithubUrl = {"https://raw.githubusercontent.com/shyche/Live/shyche/IPTV.m3u",
            "https://raw.githubusercontent.com/yuanzl77/IPTV/main/live.m3u",
            "https://raw.githubusercontent.com/YueChan/Live/main/APTV.m3u",
            "https://raw.githubusercontent.com/Kimentanm/aptv/master/m3u/iptv.m3u",
            "https://raw.githubusercontent.com/kimwang1978/collect-tv-txt/main/merged_output.m3u",       //https://live.iptv365.org/live.m3u
            "https://raw.githubusercontent.com/Guovin/iptv-api/gd/output/result.m3u",
            "https://raw.githubusercontent.com/HerbertHe/iptv-sources/gh-pages/all.m3u",
            "https://raw.githubusercontent.com/MemoryCollection/IPTV/main/data/itv.txt",     //不是很稳定    已失效,但继续保留,也许会继续更新
            "https://raw.githubusercontent.com/BurningC4/Chinese-IPTV/master/TV-IPV4.m3u",       //https://iptv.burningc4.com/TV-IPV4.m3u
            "https://raw.githubusercontent.com/vbskycn/iptv/master/tv/iptv6.m3u",      //https://live.zbds.top/tv/iptv6.m3u
            "https://raw.githubusercontent.com/vbskycn/iptv/master/tv/iptv4.m3u",  //https://live.zbds.top/tv/iptv4.m3u
            "https://raw.githubusercontent.com/YanG-1989/m3u/main/Gather.m3u",
            "https://raw.githubusercontent.com/fanmingming/live/main/tv/m3u/ipv6.m3u",   //https://live.fanmingming.com/tv/m3u/ipv6.m3u
            "https://raw.githubusercontent.com/fanmingming/live/main/tv/m3u/itv.m3u",
            "https://raw.githubusercontent.com/fanmingming/live/main/tv/m3u/index.m3u",
            "https://raw.githubusercontent.com/gnodgl/IPTV/main/IPTV.m3u",
            "https://raw.githubusercontent.com/jisoypub/iptv/main/ipv4.m3u",
            "https://raw.githubusercontent.com/jisoypub/iptv/main/ipv4_2.m3u",
            "https://raw.githubusercontent.com/jisoypub/iptv/main/ipv6.m3u",
            "https://raw.githubusercontent.com/jisoypub/iptv/main/ipv6_2.m3u",
            "https://raw.githubusercontent.com/maitel2020/iptv-self-use/main/iptv.m3u",
            "https://raw.githubusercontent.com/huang770101/my-iptv/main/IPTV-ipv4.m3u",
            "https://raw.githubusercontent.com/huang770101/my-iptv/main/IPTV-ipv6.m3u"
    };

    String []liveUrl = {"https://live.fanmingming.com/tv/m3u/ipv6.m3u",      //看起来这个会经常自动更新(或者说是访问其github同步更新的内容)
            "https://live.fanmingming.com/tv/m3u/itv.m3u",
            "https://live.fanmingming.com/tv/m3u/index.m3u",
            "https://live.iptv365.org/live.m3u",     //目前港澳台能看,分省,比较全
            "https://aktv.top/live.m3u",
            "https://iptv.burningc4.com/TV-IPV4.m3u",
            "https://live.zbds.top/tv/iptv6.m3u",
            "https://live.zbds.top/tv/iptv4.m3u",
            "http://175.178.251.183:6689/live.m3u"  //yuanzl77.github.io
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
