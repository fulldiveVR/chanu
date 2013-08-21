package com.chanapps.four.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.chanapps.four.component.ActivityDispatcher;
import com.chanapps.four.component.StringResourceDialog;
import com.chanapps.four.component.ThemeSelector;
import com.chanapps.four.data.ChanBoard;
import com.chanapps.four.data.LastActivity;
import com.chanapps.four.fragment.SettingsFragment;

/**
 * User: mpop
 * Date: 11/20/12
 * Time: 11:50 PM
 */
public class SettingsActivity extends Activity implements ChanIdentifiedActivity, ThemeSelector.ThemeActivity {

    public static final String TAG = SettingsActivity.class.getSimpleName();

    public static final String PREF_SHOW_NSFW_BOARDS = "pref_show_nsfw_boards";
    public static final String PREF_NOTIFICATIONS = "pref_notifications";
    public static final String PREF_USE_FRIENDLY_IDS = "pref_use_friendly_ids";
    public static final String PREF_THEME = "pref_theme";
    public static final String PREF_USER_NAME = "pref_user_name";
    public static final String PREF_USER_EMAIL = "pref_user_email";
    public static final String PREF_USER_PASSWORD = "pref_user_password";
    public static final String PREF_CACHE_SIZE = "pref_cache_size";
    public static final String PREF_CLEAR_CACHE = "pref_clear_cache";
    public static final String PREF_CLEAR_WATCHLIST = "pref_clear_watchlist";
    public static final String PREF_CLEAR_FAVORITES = "pref_clear_favorites";
    public static final String PREF_RESET_TO_DEFAULTS = "pref_reset_to_defaults";
    public static final String PREF_BLOCKLIST_BUTTON = "pref_blocklist_button";
    public static final String PREF_ABOUT = "pref_about";
    public static final String PREF_PASS_TOKEN = "pref_pass_token";
    public static final String PREF_PASS_PIN = "pref_pass_pin";
    public static final String PREF_PASS_ENABLED = "pref_pass_enabled";
    public static final String PREF_WIDGET_BOARDS = "prefWidgetBoards";
    public static final String PREF_BLOCKLIST_TRIPCODE = "prefBlocklistTripcode";
    public static final String PREF_BLOCKLIST_NAME = "prefBlocklistName";
    public static final String PREF_BLOCKLIST_EMAIL = "prefBlocklistEmail";
    public static final String PREF_BLOCKLIST_ID = "prefBlocklistId";

    protected int themeId;
    protected ThemeSelector.ThemeReceiver broadcastThemeReceiver;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastThemeReceiver = new ThemeSelector.ThemeReceiver(this);
        broadcastThemeReceiver.register();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public int getThemeId() {
        return themeId;
    }

    @Override
    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastThemeReceiver.unregister();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        ActivityDispatcher.store(this);
    }

    @Override
	public ChanActivityId getChanActivityId() {
		return new ChanActivityId(LastActivity.SETTINGS_ACTIVITY);
	}

	@Override
	public Handler getChanHandler() {
		return null;
	}

    @Override
    public void refresh() {}

    @Override
    public void closeSearch() {}

    @Override
    public void setProgress(boolean on) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BoardActivity.startActivity(this, ChanBoard.ALL_BOARDS_BOARD_CODE, "");
                return true;
            case R.id.global_rules_menu:
                (new StringResourceDialog(this,
                        R.layout.board_rules_dialog,
                        R.string.global_rules_header,
                        R.string.global_rules_detail))
                        .show();
                return true;
            case R.id.web_menu:
                String url = ChanBoard.boardUrl(null);
                ActivityDispatcher.launchUrlInBrowser(this, url);
            case R.id.settings_menu:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.about_menu:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

