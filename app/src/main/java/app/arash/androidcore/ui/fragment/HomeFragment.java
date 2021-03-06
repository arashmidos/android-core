package app.arash.androidcore.ui.fragment;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.arash.androidcore.R;
import app.arash.androidcore.data.entity.Constant;
import app.arash.androidcore.data.entity.Doctor;
import app.arash.androidcore.data.entity.DoctorVisit;
import app.arash.androidcore.data.entity.DrugAlarmModel;
import app.arash.androidcore.data.entity.FabChangedEvent;
import app.arash.androidcore.data.entity.FabChangedEvent.FabStatus;
import app.arash.androidcore.data.entity.RefreshEvent;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.data.impl.DoctorDaoImpl;
import app.arash.androidcore.data.impl.DoctorVisitDaoImpl;
import app.arash.androidcore.data.impl.DrugAlarmDaoImpl;
import app.arash.androidcore.service.VideoService;
import app.arash.androidcore.ui.activity.MainActivity;
import app.arash.androidcore.ui.activity.NewPhoneActivity;
import app.arash.androidcore.ui.activity.NewVisitActivity;
import app.arash.androidcore.ui.activity.VideoCategoryListActivity;
import app.arash.androidcore.ui.activity.VideoDetailActivity;
import app.arash.androidcore.ui.adapter.MedicineAdapter;
import app.arash.androidcore.ui.adapter.VideoListAdapter;
import app.arash.androidcore.ui.adapter.VisitAdapter;
import app.arash.androidcore.ui.fragment.dialog.AddDrugReminderDialogFragment;
import app.arash.androidcore.ui.fragment.dialog.DrugReminderListDialogFragment;
import app.arash.androidcore.ui.fragment.dialog.MeasureListDialogFragment;
import app.arash.androidcore.ui.fragment.dialog.NewDoctorDialogFragment;
import app.arash.androidcore.util.DateUtil;
import app.arash.androidcore.util.NumberUtil;
import app.arash.androidcore.util.PreferenceHelper;
import app.arash.androidcore.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeFragment extends BaseFragment {

  @BindView(R.id.medicine_recycler_view)
  RecyclerView medicineRecyclerView;
  Unbinder unbinder;
  @BindView(R.id.medicines_tv)
  TextView medicinesTv;
  @BindView(R.id.medicine_more_divider)
  View medicineMoreDivider;
  @BindView(R.id.more_medicine_tv)
  TextView moreMedicineTv;
  @BindView(R.id.medicine_card)
  CardView medicineCard;
  @BindView(R.id.visit_recycler_view)
  RecyclerView visitRecyclerView;
  @BindView(R.id.medicine_empty_view)
  LinearLayout medicineEmptyView;
  @BindView(R.id.visit_empty_view)
  CardView visitEmptyView;
  @BindView(R.id.toolbar_date)
  TextView toolbarDate;
  @BindView(R.id.visit_tv)
  TextView visitTv;
  @BindView(R.id.set_visit_tv)
  TextView setVisitTv;
  @BindView(R.id.overlay)
  FrameLayout overlay;
  @BindView(R.id.right_labels)
  FloatingActionsMenu fabMenu;
  @BindView(R.id.nested_lay)
  NestedScrollView nestedLay;
  @BindView(R.id.video_recycler_view)
  RecyclerView videoRecyclerView;
  @BindView(R.id.videos_tv)
  TextView videosTv;
  @BindView(R.id.new_video)
  ImageView newVideo;
  @BindView(R.id.new_video_body)
  TextView newVideoBody;
  @BindView(R.id.new_video_time)
  TextView newVideoTime;
  @BindView(R.id.new_video_devider)
  View newVideoDevider;
  @BindView(R.id.new_video_layout)
  LinearLayout newVideoLayout;

  private MedicineAdapter medicineAdapter;
  private VisitAdapter visitAdapter;
  private MainActivity mainActivity;
  private DoctorVisitDaoImpl doctorVisitDao;
  private DrugAlarmDaoImpl drugAlarmDao;
  private List<Video> videoList;
  private VideoListAdapter videoAdapter;

  public HomeFragment() {
    // Required empty public constructor
  }


  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    unbinder = ButterKnife.bind(this, view);
    mainActivity = (MainActivity) getActivity();
    setDate();
    setUpMedicineRecyclerView();
    setFABListener();
    nestedLay.setOnScrollChangeListener(
        (OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

          if (scrollY > oldScrollY) {
            fabMenu.setVisibility(View.GONE);
          }
          if (scrollY < oldScrollY) {
            fabMenu.setVisibility(View.VISIBLE);
          }
        });
    return view;
  }

  private void setFABListener() {
    fabMenu.setOnFloatingActionsMenuUpdateListener(new OnFloatingActionsMenuUpdateListener() {
      @Override
      public void onMenuExpanded() {
        overlay.setVisibility(View.VISIBLE);
        mainActivity.showOverlay(true);
      }

      @Override
      public void onMenuCollapsed() {
        overlay.setVisibility(View.GONE);
        mainActivity.showOverlay(false);
      }
    });
  }

  private void setDate() {
    doctorVisitDao = new DoctorVisitDaoImpl(mainActivity);
    drugAlarmDao = new DrugAlarmDaoImpl(mainActivity);

    try {
      String dateString = DateUtil.getFullPersianDate(new Date());
      toolbarDate.setText(String.format("امروز، %s", NumberUtil.digitsToPersian(dateString)));
    } catch (Exception ignore) {
      toolbarDate.setText("");
    }
  }

  private void setUpMedicineRecyclerView() {
    List<DrugAlarmModel> alarms = drugAlarmDao.getTodayDrugList();
    if (alarms.size() == 0) {
      medicineRecyclerView.setVisibility(View.GONE);
      medicineEmptyView.setVisibility(View.VISIBLE);
      moreMedicineTv.setVisibility(View.VISIBLE);
      medicineMoreDivider.setVisibility(View.VISIBLE);
      moreMedicineTv.setText(getString(R.string.set_drug_alarm));
    } else {
      visitEmptyView.setVisibility(View.GONE);
      medicineRecyclerView.setVisibility(View.VISIBLE);
      if (alarms.size() > 3) {
        moreMedicineTv.setVisibility(View.VISIBLE);
        medicineMoreDivider.setVisibility(View.VISIBLE);
        alarms = alarms.subList(0, 3);
      } else {
        moreMedicineTv.setVisibility(View.GONE);
        medicineMoreDivider.setVisibility(View.GONE);
      }
      medicinesTv.setVisibility(View.VISIBLE);
      medicineCard.setVisibility(View.VISIBLE);
      medicineAdapter = new MedicineAdapter(mainActivity, alarms);
      LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
      medicineRecyclerView.setAdapter(medicineAdapter);
      medicineRecyclerView.setLayoutManager(layoutManager);
    }
  }

  public void setUpVisitRecyclerView() {
    List<DoctorVisit> visits = doctorVisitDao.retrieveAllByDate();
    if (visits.size() != 0) {
      visitRecyclerView.setVisibility(View.VISIBLE);
      visitEmptyView.setVisibility(View.GONE);
      visitAdapter = new VisitAdapter(mainActivity, visits, this);
      LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
      visitRecyclerView.setAdapter(visitAdapter);
      visitRecyclerView.setLayoutManager(layoutManager);
    } else {
      visitRecyclerView.setVisibility(View.GONE);
      visitEmptyView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getFragmentId() {
    return MainActivity.HOME_FRAGMENT;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.add_visit, R.id.add_doctor, R.id.add_chart, R.id.add_reminder,
      R.id.more_medicine_tv, R.id.set_visit_tv, R.id.overlay, R.id.show_video_list_btn,
      R.id.new_video_layout})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.add_visit:
        List<Doctor> doctors = new DoctorDaoImpl(mainActivity).retrieveAll();
        if (doctors.size() > 0) {

          startActivity(new Intent(mainActivity, NewVisitActivity.class));
        } else {
          ToastUtil.toastError(mainActivity, getString(R.string.message_no_doctor_found));
        }

        fabMenu.collapse();
        break;
      case R.id.add_doctor:
        FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        NewDoctorDialogFragment newDoctorDialogFragment = NewDoctorDialogFragment
            .newInstance(mainActivity, null);
        newDoctorDialogFragment.show(ft, "new doctor");
        fabMenu.collapse();
        break;
      case R.id.add_chart:
        FragmentTransaction ft2 = mainActivity.getFragmentManager().beginTransaction();
        MeasureListDialogFragment dialogFragment = MeasureListDialogFragment
            .newInstance(mainActivity);
        dialogFragment.show(ft2, "measure list");
        fabMenu.collapse();
        break;
      case R.id.add_reminder:
        showAddReminderDialog();
        fabMenu.collapse();
        break;
      case R.id.more_medicine_tv:
        if (moreMedicineTv.getText().toString().equals(getString(R.string.set_drug_alarm))) {
          showAddReminderDialog();
        } else {
          FragmentTransaction ft3 = mainActivity.getFragmentManager().beginTransaction();
          DrugReminderListDialogFragment reminderList = DrugReminderListDialogFragment
              .newInstance(mainActivity);
          reminderList.show(ft3, "reminder list");
        }
        break;
      case R.id.set_visit_tv:
        List<Doctor> doctors2 = new DoctorDaoImpl(mainActivity).retrieveAll();
        if (doctors2.size() > 0) {
          startActivity(new Intent(mainActivity, NewVisitActivity.class));
        } else {
          ToastUtil.toastError(mainActivity, getString(R.string.message_no_doctor_found));
        }
        fabMenu.collapse();
        break;
      case R.id.overlay:
        overlay.setVisibility(View.GONE);
        fabMenu.collapse();
        break;
      case R.id.show_video_list_btn:
        startActivity(new Intent(mainActivity, VideoCategoryListActivity.class));
        break;
      case R.id.new_video_layout:
        Intent intent = new Intent(mainActivity, VideoDetailActivity.class);
        intent.putExtra(Constant.VIDEO, videoList.get(0));
        mainActivity.startActivity(intent);
    }
  }

  private void showAddReminderDialog() {
    FragmentTransaction ftAddDrug = mainActivity.getFragmentManager().beginTransaction();
    AddDrugReminderDialogFragment addDrugReminderDialogFragment = AddDrugReminderDialogFragment
        .newInstance(mainActivity, null);
    addDrugReminderDialogFragment.show(ftAddDrug, "add drug reminder");
  }

  @Override
  public void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
    setUpMedicineRecyclerView();
    setUpVisitRecyclerView();
    new VideoService().getVideoList(null, 5);
  }

  @Subscribe
  public void getMessage(FabChangedEvent event) {
    if (event.getFabStatus() == FabStatus.COLLAPSED) {
      fabMenu.collapse();
    }
  }

  @Subscribe
  public void getMessage(RefreshEvent event) {
    setUpMedicineRecyclerView();
    setUpVisitRecyclerView();
  }

  @Subscribe
  public void getMessage(ErrorEvent event) {
    switch (event.getStatusCode()) {
      case NO_NETWORK:
        ToastUtil.toastError(mainActivity, R.string.error_no_network);
        break;
      case AUTHENTICATE_ERROR:
        ToastUtil.toastError(mainActivity, getString(R.string.credit_low));
        Intent intent = new Intent(mainActivity, NewPhoneActivity.class);
        PreferenceHelper.setToken("");
        PreferenceHelper.setPhoneNumber("");
        startActivity(intent);
        mainActivity.finish();
        break;
      case NETWORK_ERROR:
        ToastUtil.toastError(mainActivity, getString(R.string.error_in_connecting_to_server));
        break;
      case NO_DATA_ERROR:
        ToastUtil.toastError(mainActivity, getString(R.string.no_data_found));
        break;
      case SERVER_ERROR:
        ToastUtil.toastError(mainActivity, getString(R.string.error_server));
        break;
    }
  }

  @Subscribe
  public void getMessage(VideoEvent event) {
    setupVideoRecycler(event.getVideoList());
  }

  private void setupVideoRecycler(List<Video> videoList) {
    this.videoList = new ArrayList<>();
    this.videoList.addAll(videoList);
    if (videoList.size() > 0) {
      Video promoVideo = videoList.get(0);
      newVideoLayout.setVisibility(View.VISIBLE);
      Glide.with(mainActivity).load(promoVideo.getImagePreview()).into(newVideo);
      newVideoBody.setText(promoVideo.getTitle());
      newVideoTime.setText(NumberUtil.digitsToPersian(String.format(Locale.US, "%02d:%02d",
          promoVideo.getLength() / 60, promoVideo.getLength() % 60)));
    }
    if (videoList.size() > 1) {
      videoList.remove(0);
      videoAdapter = new VideoListAdapter(mainActivity, videoList);
      videoRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
      videoRecyclerView.setAdapter(videoAdapter);
    }
  }

}
