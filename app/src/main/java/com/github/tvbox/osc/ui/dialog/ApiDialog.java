package com.github.tvbox.osc.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.tvbox.osc.R;
import com.github.tvbox.osc.event.RefreshEvent;
import com.github.tvbox.osc.server.ControlManager;
import com.github.tvbox.osc.ui.activity.HomeActivity;
import com.github.tvbox.osc.ui.adapter.ApiHistoryDialogAdapter;
import com.github.tvbox.osc.ui.tv.QRCodeGen;
import com.github.tvbox.osc.util.DefaultConfig;
import com.github.tvbox.osc.util.HawkConfig;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 描述
 *
 * @author pj567
 * @since 2020/12/27
 */
public class ApiDialog extends BaseDialog {
    private final ImageView ivQRCode;
    private final TextView tvAddress;
    private final EditText inputApi;
    private final EditText inputLive;
    private final EditText inputEPG;
    private final EditText inputConfigProxy;
    private final EditText inputProxy;
    private final CheckBox useAPILive;

//https://ghproxy.net/https://raw.githubusercontent.com/anaer/Meow/main/meow.json
//    https://mirror.ghproxy.com/https://raw.githubusercontent.com/bizhangjie/CatVodSpiderJS/main/json/18sex.json
//https://盒子迷.top/禁止贩卖  //多而全
//    https://szyyds.cn/tv/x.json   //小马线路 可以
//    https://mirror.ghproxy.com/https://raw.githubusercontent.com/shyche/ftybendi/main/fty3/fty.json   //饭太硬备份
//    https://yydf.540734621.xyz/QQ/yydf2024.json   //首页无显示,功能正常,部分数据源导致crash,慎改
//    http://ok321.top/ok       //可用,不能直接下载
//    http://pandown.pro/tvbox/tvbox.json   //巧儿


    private final String inputAPITextDefault = "http://肥猫.com";
//    private final String inputAPITextDefault = "http://饭太硬.com/tv";
//    private final String inputAPITextDefault =  "https://mirror.ghproxy.com/https://raw.githubusercontent.com/shyche/ftybendi/main/fty3/fty.json";
//    private final String inputLiveTextDefault = "https://raw.githubusercontent.com/shyche/Live/shyche/IPTV.m3u";
    private final String inputLiveTextDefault = "https://raw.githubusercontent.com/shyche/Live/shyche/IPTV.m3u;https://raw.githubusercontent.com/kimwang1978/collect-tv-txt/main/merged_output.m3u;https://raw.githubusercontent.com/yuanzl77/IPTV/main/live.m3u;https://raw.githubusercontent.com/YanG-1989/m3u/main/Gather.m3u;https://raw.githubusercontent.com/Guovin/iptv-api/gd/output/result.m3u;https://live.fanmingming.com/tv/m3u/ipv6.m3u;https://raw.githubusercontent.com/fanmingming/live/main/tv/m3u/ipv6.m3u;https://live.iptv365.org/live.m3u;http://175.178.251.183:6689/live.m3u";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshEvent event) {
        if (event.type == RefreshEvent.TYPE_API_URL_CHANGE) {
            inputApi.setText((String) event.obj);
        }
        if (event.type == RefreshEvent.TYPE_LIVE_URL_CHANGE) {
            inputLive.setText((String) event.obj);
        }
        if (event.type == RefreshEvent.TYPE_EPG_URL_CHANGE) {
            inputEPG.setText((String) event.obj);
        }
        if (event.type == RefreshEvent.TYPE_PROXYS_CHANGE) {
            inputProxy.setText((String) event.obj);
        }
    }

    public ApiDialog(@NonNull @NotNull Context context) {
        super(context);
        setContentView(R.layout.dialog_api);
        setCanceledOnTouchOutside(true);
        ivQRCode = findViewById(R.id.ivQRCode);
        tvAddress = findViewById(R.id.tvAddress);
        inputApi = findViewById(R.id.input);
        inputApi.setText(Hawk.get(HawkConfig.API_URL, inputAPITextDefault));

        useAPILive = findViewById(R.id.useAPILive);

        // takagen99: Add Live & EPG Address
        inputLive = findViewById(R.id.input_live);
        inputLive.setText(Hawk.get(HawkConfig.LIVE_URL, inputLiveTextDefault));
        inputEPG = findViewById(R.id.input_epg);
        inputEPG.setText(Hawk.get(HawkConfig.EPG_URL, ""));

        //shyche
        inputConfigProxy = findViewById(R.id.inputConfigProxy);
        inputConfigProxy.setText(Hawk.get(HawkConfig.INPUT_CONFIG_PROXY_URL));

        inputProxy = findViewById(R.id.input_proxy);
        inputProxy.setText(Hawk.get(HawkConfig.PROXY_SERVER, ""));

        findViewById(R.id.inputSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newApi = inputApi.getText().toString().trim();
                String newLive = inputLive.getText().toString().trim();
                String newEPG = inputEPG.getText().toString().trim();
                String newInputConfigProxy = inputConfigProxy.getText().toString().trim();
                String newProxyServer = inputProxy.getText().toString().trim();
                // takagen99: Convert all to clan://localhost format
                if (newApi.startsWith("file://")) {
                    newApi = newApi.replace("file://", "clan://localhost/");
                } else if (newApi.startsWith("./")) {
                    newApi = newApi.replace("./", "clan://localhost/");
                }
                if (!newApi.isEmpty()) {
                    ArrayList<String> history = Hawk.get(HawkConfig.API_HISTORY, new ArrayList<String>());
                    if (!history.contains(newApi))
                        history.add(0, newApi);
                    if (history.size() > 20)
                        history.remove(20);
                    Hawk.put(HawkConfig.API_HISTORY, history);
                    listener.onchange(newApi);
                    dismiss();
                }
                // Capture Live input into Settings & Live History (max 20)
                if (!useAPILive.isChecked()) {
                    Hawk.put(HawkConfig.LIVE_URL, newLive);
                    if (!newLive.isEmpty() ) {
                        ArrayList<String> liveHistory = Hawk.get(HawkConfig.LIVE_HISTORY, new ArrayList<String>());
                        if (!liveHistory.contains(newLive))
                            liveHistory.add(0, newLive);
                        if (liveHistory.size() > 20)
                            liveHistory.remove(20);
                        Hawk.put(HawkConfig.LIVE_HISTORY, liveHistory);
                    }
                }

                // Capture EPG input into Settings
                Hawk.put(HawkConfig.EPG_URL, newEPG);
                if (!newEPG.isEmpty()) {
                    ArrayList<String> EPGHistory = Hawk.get(HawkConfig.EPG_HISTORY, new ArrayList<String>());
                    if (!EPGHistory.contains(newEPG))
                        EPGHistory.add(0, newEPG);
                    if (EPGHistory.size() > 20)
                        EPGHistory.remove(20);
                    Hawk.put(HawkConfig.EPG_HISTORY, EPGHistory);
                }

                Hawk.put(HawkConfig.INPUT_CONFIG_PROXY_URL, newInputConfigProxy);
                if (!newInputConfigProxy.isEmpty()) {
                    ArrayList<String> inputConfigProxyHistory = Hawk.get(HawkConfig.INPUT_CONFIG_PROXY_HISTORY, new ArrayList<String>());
                    if (!inputConfigProxyHistory.contains(newInputConfigProxy))
                        inputConfigProxyHistory.add(0, newInputConfigProxy);
                    if (inputConfigProxyHistory.size() > 20)
                        inputConfigProxyHistory.remove(20);
                    Hawk.put(HawkConfig.INPUT_CONFIG_PROXY_HISTORY, inputConfigProxyHistory);
                }

                // Capture oroxy server input into Settings
                Hawk.put(HawkConfig.PROXY_SERVER, newProxyServer);
            }
        });
        findViewById(R.id.apiHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> history = Hawk.get(HawkConfig.API_HISTORY, new ArrayList<String>());
                if (history.isEmpty())
                    return;
                String current = Hawk.get(HawkConfig.API_URL, "");
                int idx = 0;
                if (history.contains(current))
                    idx = history.indexOf(current);
                ApiHistoryDialog dialog = new ApiHistoryDialog(getContext());
                dialog.setTip(HomeActivity.getRes().getString(R.string.dia_history_list));
                dialog.setAdapter(new ApiHistoryDialogAdapter.SelectDialogInterface() {
                    @Override
                    public void click(String value) {
                        inputApi.setText(value);
                        listener.onchange(value);
                        dialog.dismiss();
                    }

                    @Override
                    public void del(String value, ArrayList<String> data) {
                        Hawk.put(HawkConfig.API_HISTORY, data);
                    }
                }, history, idx);
                dialog.show();
            }
        });
        findViewById(R.id.liveHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> liveHistory = Hawk.get(HawkConfig.LIVE_HISTORY, new ArrayList<String>());
                if (liveHistory.isEmpty())
                    return;
                String current = Hawk.get(HawkConfig.LIVE_URL, "");
                int idx = 0;
                if (liveHistory.contains(current))
                    idx = liveHistory.indexOf(current);
                ApiHistoryDialog dialog = new ApiHistoryDialog(getContext());
                dialog.setTip(HomeActivity.getRes().getString(R.string.dia_history_live));
                dialog.setAdapter(new ApiHistoryDialogAdapter.SelectDialogInterface() {
                    @Override
                    public void click(String liveURL) {
                        inputLive.setText(liveURL);
                        Hawk.put(HawkConfig.LIVE_URL, liveURL);
                        dialog.dismiss();
                    }

                    @Override
                    public void del(String value, ArrayList<String> data) {
                        Hawk.put(HawkConfig.LIVE_HISTORY, data);
                    }
                }, liveHistory, idx);
                dialog.show();
            }
        });
        findViewById(R.id.EPGHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> EPGHistory = Hawk.get(HawkConfig.EPG_HISTORY, new ArrayList<String>());
                if (EPGHistory.isEmpty())
                    return;
                String current = Hawk.get(HawkConfig.EPG_URL, "");
                int idx = 0;
                if (EPGHistory.contains(current))
                    idx = EPGHistory.indexOf(current);
                ApiHistoryDialog dialog = new ApiHistoryDialog(getContext());
                dialog.setTip(HomeActivity.getRes().getString(R.string.dia_history_epg));
                dialog.setAdapter(new ApiHistoryDialogAdapter.SelectDialogInterface() {
                    @Override
                    public void click(String epgURL) {
                        inputEPG.setText(epgURL);
                        Hawk.put(HawkConfig.EPG_URL, epgURL);
                        dialog.dismiss();
                    }

                    @Override
                    public void del(String value, ArrayList<String> data) {
                        Hawk.put(HawkConfig.EPG_HISTORY, data);
                    }
                }, EPGHistory, idx);
                dialog.show();
            }
        });

        findViewById(R.id.inputConfigProxyHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> history = Hawk.get(HawkConfig.INPUT_CONFIG_PROXY_HISTORY, new ArrayList<String>());
                if (history.isEmpty())
                    return;
                String current = Hawk.get(HawkConfig.INPUT_CONFIG_PROXY_URL, "");
                int idx = 0;
                if (history.contains(current))
                    idx = history.indexOf(current);
                ApiHistoryDialog dialog = new ApiHistoryDialog(getContext());
                dialog.setTip(HomeActivity.getRes().getString(R.string.dia_history_list));
                dialog.setAdapter(new ApiHistoryDialogAdapter.SelectDialogInterface() {
                    @Override
                    public void click(String value) {
                        inputApi.setText(value);
                        listener.onchange(value);
                        dialog.dismiss();
                    }

                    @Override
                    public void del(String value, ArrayList<String> data) {
                        Hawk.put(HawkConfig.API_HISTORY, data);
                    }
                }, history, idx);
                dialog.show();
            }
        });

        findViewById(R.id.storagePermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XXPermissions.isGranted(getContext(), DefaultConfig.StoragePermissionGroup())) {
                    Toast.makeText(getContext(), "已获得存储权限", Toast.LENGTH_SHORT).show();
                } else {
                    XXPermissions.with(getContext())
                            .permission(DefaultConfig.StoragePermissionGroup())
                            .request(new OnPermissionCallback() {
                                @Override
                                public void onGranted(List<String> permissions, boolean all) {
                                    if (all) {
                                        Toast.makeText(getContext(), "已获得存储权限", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onDenied(List<String> permissions, boolean never) {
                                    if (never) {
                                        Toast.makeText(getContext(), "获取存储权限失败,请在系统设置中开启", Toast.LENGTH_SHORT).show();
                                        XXPermissions.startPermissionActivity((Activity) getContext(), permissions);
                                    } else {
                                        Toast.makeText(getContext(), "获取存储权限失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        inputApi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // TextView 失去焦点时的处理逻辑
                    if (inputApi.getText().length() == 0) {
                        inputApi.setText(inputAPITextDefault);
                    }
                }
            }
        });

        inputLive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // TextView 失去焦点时的处理逻辑.此项其实可以置空,因为数据源地址中一般有.
                    if (inputLive.getText().length() == 0) {
                        inputLive.setText(inputLiveTextDefault);
                    }
                }
            }
        });

        refreshQRCode();
    }

    private void refreshQRCode() {
        String address = ControlManager.get().getAddress(false);
        tvAddress.setText(String.format("手机/电脑扫描上方二维码或者直接浏览器访问地址\n%s", address));
        ivQRCode.setImageBitmap(QRCodeGen.generateBitmap(address, AutoSizeUtils.mm2px(getContext(), 300), AutoSizeUtils.mm2px(getContext(), 300)));
    }

    public void setOnListener(OnListener listener) {
        this.listener = listener;
    }

    OnListener listener = null;

    public interface OnListener {
        void onchange(String api);
    }
}