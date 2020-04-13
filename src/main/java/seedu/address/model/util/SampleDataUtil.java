package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.consult.ReadOnlyConsult;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialName;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.mod.ReadOnlyMod;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.Email;
import seedu.address.model.student.MatricNumber;
import seedu.address.model.student.Name;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StudentTAble} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new MatricNumber("A0187596R"), new Email("alexyeoh@example.com"),
                getTagSet("too quiet")),
            new Student(new Name("Bernice Yu"), new MatricNumber("A0125875G"), new Email("berniceyu@example.com"),
                getTagSet("perfect", "hardworking", "future TA")),
            new Student(new Name("Charlotte Oliveiro"), new MatricNumber("A0123456A"),
                    new Email("charlotte@example.com"), getTagSet("noisy", "distracting",
                    "2nd time as my student")),
            new Student(new Name("David Li"), new MatricNumber("A0356982H"), new Email("lidavid@example.com"),
                getTagSet()),
            new Student(new Name("Irfan Ibrahim"), new MatricNumber("A0167954H"), new Email("irfan@example.com"),
                getTagSet("always late")),
            new Student(new Name("Roy Balakrishnan"), new MatricNumber("A0136975F"), new Email("royb@example.com"),
                getTagSet())
        };
    }

    public static ReadOnlyStudent getSampleAddressBook() {
        StudentTAble sampleAb = new StudentTAble();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static ReadOnlyConsult getSampleConsults() {
        LocalDateTime startDateTime = LocalDateTime.parse("2020-04-03 10:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = startDateTime.plusHours(3);
        Location location = new Location("SR1");
        Name studentName = new Name("Alex Yeoh");
        MatricNumber matricNumber = new MatricNumber("A0187596R");

        Consult consult = new Consult(startDateTime, endDateTime, location, studentName, matricNumber);
        ArrayList<Consult> consultList = new ArrayList<>();
        consultList.add(consult);
        ConsultTAble sampleConsultTAble = new ConsultTAble(consultList);
        return sampleConsultTAble;
    }

    public static ReadOnlyTutorial getSampleTutorials() {
        ModCode modCode = new ModCode("CS2103");
        TutorialName tutorialName = new TutorialName("T03");
        DayOfWeek weekday = DayOfWeek.WEDNESDAY;
        LocalTime startTime = LocalTime.of(15, 0);
        LocalTime endTime = startTime.plusHours(2);
        Location location = new Location("SR1");
        Tutorial tutorial = new Tutorial(modCode, tutorialName, weekday, startTime, endTime, location);
        Student alex = new Student(new Name("Alex Yeoh"), new MatricNumber("A0187596R"),
                new Email("alexyeoh@example.com"), getTagSet("too quiet"));
        Student bernice = new Student(new Name("Bernice Yu"), new MatricNumber("A0125875G"),
                new Email("berniceyu@example.com"), getTagSet("perfect", "hardworking", "future TA"));
        Student charlotte = new Student(new Name("Charlotte Oliveiro"), new MatricNumber("A0123456A"),
                new Email("charlotte@example.com"), getTagSet("noisy", "distracting", "2nd time as my student"));
        tutorial.setEnrolledStudents(alex);
        tutorial.setEnrolledStudents(bernice);
        tutorial.setEnrolledStudents(charlotte);
        ArrayList<Tutorial> tutorialList = new ArrayList<>();
        tutorialList.add(tutorial);
        TutorialTAble sampleTutorialTAble = new TutorialTAble(tutorialList);
        return sampleTutorialTAble;
    }

    public static ReadOnlyMod getSampleMods() {
        ModCode modCode = new ModCode("CS2103");
        String modName = "Software Engineering";
        Mod mod = new Mod(modCode, modName);
        mod = new Mod(mod, "SE is fun!!");
        mod = new Mod(mod, "Module Website", new ModLink("http://www.comp.nus.edu.sg/~cs2103"));
        mod = new Mod(mod, "Best Student Project",
            new ModLink("https://github.com/AY1920S2-CS2103-W15-3/main"));
        ArrayList<Mod> modList = new ArrayList<>();
        modList.add(mod);
        ModTAble sampleModTAble = new ModTAble(modList);
        return sampleModTAble;
    }

    public static ReadOnlyReminder getSampleReminders() {
        Reminder[] sampleReminderList = new Reminder[] {
            new Reminder(new Description("Review project progress of group 3"),
                    LocalDate.now().minusDays(1), LocalTime.now(), false),
            new Reminder(new Description("Submit students' attendance for week 7"),
                    LocalDate.now().plusDays(2), LocalTime.now(), false),
            new Reminder(new Description("Email T03 tutorial 6 solutions"),
                    LocalDate.now().minusDays(1), LocalTime.now(), true)
        };

        ArrayList<Reminder> reminderList = new ArrayList<>(Arrays.asList(sampleReminderList));
        return new ReminderTAble(reminderList);
    }

}
