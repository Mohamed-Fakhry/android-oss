package com.kickstarter.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kickstarter.KsrApplication;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.CurrentUser;
import com.kickstarter.libs.RequiresPresenter;
import com.kickstarter.models.Activity;
import com.kickstarter.presenters.ActivityFeedPresenter;
import com.kickstarter.ui.adapters.ActivityListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

@RequiresPresenter(ActivityFeedPresenter.class)
public class ActivityFeedActivity extends BaseActivity<ActivityFeedPresenter> {
  ActivityListAdapter adapter;
  @Optional @InjectView(R.id.discover_projects_button) Button discoverProjectsButton;
  @Optional @InjectView(R.id.recyclerView) RecyclerView recyclerView;
  @Inject CurrentUser currentUser;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ((KsrApplication) getApplication()).component().inject(this);
    final int layout = currentUser.exists() ? R.layout.activity_feed_layout : R.layout.empty_activity_feed_layout;
    setContentView(layout);
    ButterKnife.inject(this);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  public void discoverProjectsButtonOnClick(final View view) {
    final Intent intent = new Intent(this, DiscoveryActivity.class);
    startActivity(intent);
  }

  public void onItemsNext(final List<Activity> activities) {
    adapter = new ActivityListAdapter(activities, presenter);
    recyclerView.setAdapter(adapter);
  }
}
