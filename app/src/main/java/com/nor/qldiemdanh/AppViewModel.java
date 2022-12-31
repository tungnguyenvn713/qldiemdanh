package com.nor.qldiemdanh;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.model.Admin;
import com.nor.qldiemdanh.model.CheckIn;
import com.nor.qldiemdanh.model.CheckInList;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Register;
import com.nor.qldiemdanh.model.Room;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.Student;
import com.nor.qldiemdanh.model.Subject;
import com.nor.qldiemdanh.model.Teacher;
import com.nor.qldiemdanh.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(Const.DB_ROOT);
    private MutableLiveData<List<Subject>> subjects = new MutableLiveData<>();
    private MutableLiveData<List<Teacher>> teachers = new MutableLiveData<>();
    private MutableLiveData<List<Student>> students = new MutableLiveData<>();
    private MutableLiveData<List<Student>> studentsRegistered = new MutableLiveData<>();
    private MutableLiveData<List<Schedule>> schedules = new MutableLiveData<>();
    private MutableLiveData<List<Room>> rooms = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<Const.CHECK_IN_STATUS> checkIn = new MutableLiveData<>();
    private MutableLiveData<List<CheckInList>> checkInList = new MutableLiveData<>();
    private long countSchedule;
    private long countRegistered;

    public AppViewModel(@NonNull Application application) {
        super(application);
        registerData(new Student(), students);
        registerData(new Teacher(), teachers);
        registerData(new Subject(), subjects);
        registerData(new Room(), rooms);
    }

    public void registerSchedule() {
        countSchedule = 0;
        Query query = reference.child(Const.ROOT_SCHEDULE);
        query.addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Schedule> arr = new ArrayList<>();
                if (dataSnapshot.getChildrenCount() == 0) {
                    schedules.postValue(arr);
                    return;
                }
                countSchedule = dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Schedule schedule = snapshot.getValue(Schedule.class);
                    final String userId = user.getValue().getId();
                    reference.child(Const.ROOT_REGISTER).child(schedule.getId()).orderByChild("idStudent").equalTo(userId)
                            .addValueEventListener(new DataChangeListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    schedule.setRegistered(dataSnapshot.getChildrenCount() > 0);
                                }
                            });
                    reference.child(Const.ROOT_ROOM).orderByChild("id").equalTo(schedule.getIdRoom()).addValueEventListener(new DataChangeListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() == 0) {
                                if (--countSchedule == 0) {
                                    schedules.postValue(arr);
                                }
                            } else {
                                for (DataSnapshot s : dataSnapshot.getChildren()) {
                                    Room room = s.getValue(Room.class);
                                    schedule.setRoomName(room.getName());
                                    break;
                                }
                                reference.child(Const.ROOT_TEACHER).orderByChild("id").equalTo(schedule.getIdTeacher()).addValueEventListener(new DataChangeListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getChildrenCount() == 0) {
                                            if (--countSchedule == 0) {
                                                schedules.postValue(arr);
                                            }
                                        } else {
                                            for (DataSnapshot s : dataSnapshot.getChildren()) {
                                                Teacher teacher = s.getValue(Teacher.class);
                                                schedule.setTeacher(teacher);
                                                break;
                                            }
                                            reference.child(Const.ROOT_SUBJECT).orderByChild("id").equalTo(schedule.getIdSubject()).addValueEventListener(new DataChangeListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getChildrenCount() == 0) {
                                                        if (--countSchedule == 0) {
                                                            schedules.postValue(arr);
                                                        }
                                                    } else {
                                                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                                                            Subject subject = s.getValue(Subject.class);
                                                            schedule.setSubject(subject);
                                                            break;
                                                        }
                                                        if (user.getValue().isTeacher()){
                                                            if (schedule.getIdTeacher().equals(user.getValue().getId())) {
                                                                arr.add(schedule);
                                                            }
                                                        }else {
                                                            arr.add(schedule);
                                                        }
                                                        if (--countSchedule == 0) {
                                                            schedules.postValue(arr);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void login(final String email, final String password) {
        Query query = reference.child(Const.ROOT_ADMIN).orderByChild("email").equalTo(email);
        query.addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    Query query = reference.child(Const.ROOT_STUDENT).orderByChild("email").equalTo(email);
                    query.addValueEventListener(new DataChangeListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() == 0) {
                                Query query = reference.child(Const.ROOT_TEACHER).orderByChild("email").equalTo(email);
                                query.addValueEventListener(new DataChangeListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Teacher teacher = snapshot.getValue(Teacher.class);
                                            if (teacher.getPassword().equals(password)) {
                                                user.postValue(teacher);
                                                AppContext.getInstance().isTeacher = true;
                                                return;
                                            }
                                        }
                                        user.postValue(null);
                                    }
                                });
                                return;
                            }
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Student student = snapshot.getValue(Student.class);
                                if (student.getPassword().equals(password)) {
                                    user.postValue(student);
                                    AppContext.getInstance().isStudent = true;
                                    return;
                                }
                            }
                            user.postValue(null);
                        }
                    });
                    return;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Admin admin = snapshot.getValue(Admin.class);
                    if (admin.getPassword().equals(password)) {
                        AppViewModel.this.user.postValue(admin);
                        AppContext.getInstance().isAdmin = true;
                        return;
                    }
                }
                user.postValue(null);
            }
        });
    }

    public void studentRegistered(String idSchedule) {
        Query query = reference.child(Const.ROOT_REGISTER).child(idSchedule);
        query.addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final List<Student> arr = new ArrayList<>();
                countRegistered = dataSnapshot.getChildrenCount();
                studentsRegistered.postValue(new ArrayList<Student>());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Register register = snapshot.getValue(Register.class);
                    reference.child(Const.ROOT_STUDENT).orderByChild("id").equalTo(register.getIdStudent())
                            .addValueEventListener(new DataChangeListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                                        Student student = sn.getValue(Student.class);
                                        arr.add(student);
                                    }
                                    if (--countRegistered == 0) {
                                        studentsRegistered.postValue(arr);
                                    }
                                }
                            });
                }
            }
        });
    }

    public void checkIn(final String scheduleId) {
        Query query = reference.child(Const.ROOT_REGISTER).child(scheduleId).orderByChild("idStudent").equalTo(user.getValue().getId());
        query.addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    checkIn.postValue(Const.CHECK_IN_STATUS.NOT_IN);
                    return;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Register register = snapshot.getValue(Register.class);
                    final Calendar calendar = Calendar.getInstance();
                    int today = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    reference.child(Const.ROOT_SCHEDULE).child(scheduleId).child(Const.ROOT_SCHEDULE_DETAIL)
                            .orderByChild("day").equalTo(today).addValueEventListener(new DataChangeListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() == 0){
                                checkIn.postValue(Const.CHECK_IN_STATUS.NOT_TODAY);
                            }else{
                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                String day = format.format(calendar.getTime());
                                CheckIn checkIn = new CheckIn();
                                checkIn.setDate(calendar.getTimeInMillis());
                                checkIn.setIdSchedule(scheduleId);
                                checkIn.setIdStudent(user.getValue().getId());
                                reference.child(Const.ROOT_CHECK_IN)
                                        .child(scheduleId)
                                        .child(day)
                                        .child(checkIn.getIdStudent())
                                        .setValue(checkIn);
                                AppViewModel.this.checkIn.postValue(Const.CHECK_IN_STATUS.SUCCESS);
                            }
                        }
                    });
                }
            }
        });
    }
    
    public void getCheckIn(String idSchedule){
        reference.child(Const.ROOT_CHECK_IN).child(idSchedule).addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList
                        <CheckInList> arr = new ArrayList<>();
                for (DataSnapshot  snapshot:dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    CheckInList list = new CheckInList();
                    list.setDate(key);
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        CheckIn checkIn = snapshot1.getValue(CheckIn.class);
                        list.addValue(checkIn);
                    }
                    arr.add(list);
                }
                checkInList.postValue(arr);
            }
        });
    }

    private <T extends Entity> void registerData(final T instance, final MutableLiveData data) {
        registerData(reference.child(instance.getRoot()), instance, data);
    }

    private <T extends Entity> void registerData(Query query, final T instance, final MutableLiveData data) {
        query.addValueEventListener(new DataChangeListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> arr = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T t = (T) snapshot.getValue(instance.getClass());
                    arr.add(t);
                }
                data.postValue(arr);
            }
        });
    }

    public MutableLiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public MutableLiveData<List<Teacher>> getTeachers() {
        return teachers;
    }

    public MutableLiveData<List<Student>> getStudents() {
        return students;
    }

    public MutableLiveData<List<Schedule>> getSchedules() {
        return schedules;
    }

    public MutableLiveData<List<Room>> getRooms() {
        return rooms;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<List<Student>> getStudentsRegistered() {
        return studentsRegistered;
    }

    public MutableLiveData<Const.CHECK_IN_STATUS> getCheckIn() {
        return checkIn;
    }

    public MutableLiveData<List<CheckInList>> getCheckInList() {
        return checkInList;
    }

    abstract class DataChangeListener implements ValueEventListener {

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
