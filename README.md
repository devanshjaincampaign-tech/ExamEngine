# Exam Engine

Java Swing mini-project for conducting an objective exam. It demonstrates
object-oriented programming, Swing UI components, threads, custom exceptions,
object serialization and random-access file storage.

## 1. Requirements

- Windows 10 or Windows 11
- JDK 17 or newer
- Java installed and available from Command Prompt
- A desktop environment that can display Swing windows

Verify the installation:

```bat
java -version
javac -version
```

Both commands should print Java version information.

## 2. Project Location

Open Command Prompt or PowerShell in:

```text
<EXAM_ENGINE_PROJECT_FOLDER>
```

The project contains:

```text
ExamEngine/
|-- build.bat
|-- run.bat
|-- README.md
|-- NOTES.md
|-- data/
|   `-- questions.txt
`-- src/
    |-- bin/
    |-- exceptions/
    |-- fileio/
    |-- model/
    |-- service/
    |-- timer/
    `-- ui/
```

## 3. Build and Run

### Recommended method

From the project root, double-click `run.bat` or enter:

```bat
run.bat
```

`run.bat` performs both actions:

1. Calls `build.bat`.
2. Builds all Java files under `src`.
3. Opens the login window.

### Build without opening the application

```bat
build.bat
```

After success, open the application manually:

```bat
java -cp "<EXAM_ENGINE_PROJECT_FOLDER>\src\bin" ui.LoginFrame
```

Quoted classpaths are important because the project path contains spaces.

### PowerShell build

Run this from the `src` directory:

```powershell
$sources = Get-ChildItem -Recurse -File -Filter '*.java' |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d bin $sources
java -cp bin ui.LoginFrame
```

## 4. Operating the Application

### Step 1: Login

The login window opens with three controls:

- Student Name
- Roll Number
- Clear and Start Exam buttons

Enter both values. Empty values are rejected.

Click **Start Exam**.

### Step 2: Answer questions

The exam window displays:

- Student name and roll number
- Remaining time
- One question at a time
- Previous and Next buttons
- Submit Exam button

For a multiple-choice question, select one radio button.

For a true/false question, select True or False.

For a short-answer question, type in the text field.
The answer check ignores capitalization and surrounding punctuation or spaces.

Use **Previous** and **Next** to move between questions.
Selected answers remain stored when navigating.

### Step 3: Submit

Click **Submit Exam** before time expires.

The result window displays:

- Student name and roll number
- Marks earned and maximum marks
- Status and percentage
- Number of correct answers
- A passed or failed badge

Example:

```text
Final Score: 10.0 / 15.0
Status: PASSED (66.7%)
Correct Answers: 2 / 3
```

If the timer reaches zero, the application submits automatically.

### Step 4: Check history

`ExamService` saves submissions automatically.

- `data/results.dat` contains serialized exam results.
- `data/index.dat` contains fixed-size index records.

These files are created automatically if they do not exist.

## 5. Editing the Question Bank

Open:

```text
data/questions.txt
```

Use one record per line.

### Multiple-choice format

```text
MCQ|ID|Prompt|Marks|Option1,Option2,Option3,Option4|CorrectOption
```

Example:

```text
MCQ|Q1|What is heap memory used for?|5|A) Local variables,B) Object allocation,C) Method metadata,D) Instructions|B
```

The correct option must match the beginning letter or the complete option text
that the application uses.

### True/false format

```text
TF|ID|Prompt|Marks|true
```

Example:

```text
TF|Q2|RandomAccessFile records are 56 bytes.|5|true
```

The answer must be `true` or `false`.

### Short-answer format

```text
SA|ID|Prompt|Marks|ExpectedAnswer
```

Multiple accepted answers can be separated by commas:

```text
SA|Q3|Which Java keyword creates a subclass?|5|extends,inherit
```

Use `#` at the beginning of a line for a comment.

After editing `questions.txt`, run `run.bat` again.

## 6. File I/O Test

The project also has a storage demonstration.

From the project root:

```bat
cd src
java -cp bin fileio.Main
```

This test saves and reloads sample results and writes index records.

## 7. Project Structure

| Package or file | Purpose |
|---|---|
| `model/` | Student, exam, questions and result objects |
| `ui/` | Login, exam, result and history windows |
| `service/` | Submission and grading |
| `fileio/` | Question loading and persistent storage |
| `timer/` | Countdown timer and callbacks |
| `exceptions/` | Custom project exceptions |
| `data/questions.txt` | Question-bank source |
| `data/results.dat` | Serialized result history |
| `data/index.dat` | Fixed-length result index |

## 8. Scoring Rules

Each question contributes the marks declared in `questions.txt`.

The grading service:

1. Reads the student's answer for each question ID.
2. Calls `checkAnswer()`.
3. Adds that question's marks when correct.
4. Counts one correct answer for the summary.

The pass mark is 50 percent of maximum marks.

Therefore, if maximum marks equal 15:

- 10 marks = 66.67 percent = PASSED
- 7 marks = 46.67 percent = FAILED

## 9. Troubleshooting

### `javac` is not recognized

JDK is missing or not on `PATH`.

Install JDK and add:

```text
<JDK>\bin
```

to the Windows `PATH`, then reopen Command Prompt.

### `Could not find or load main class`

The classpath or working directory may be wrong.

Use the absolute command:

```bat
java -cp "<EXAM_ENGINE_PROJECT_FOLDER>\src\bin" ui.LoginFrame
```

### Questions do not appear

Check `data/questions.txt`:

- It must contain one valid record per line.
- MCQ lines need a correct field.
- TF lines must contain `true` or `false`.
- SA lines must contain an answer.
- The format must use `|` separators.

### Old compiled files are used

Run:

```bat
build.bat
```

The script recompiles every `.java` source under `src`.

### GUI does not open

Make sure Java Swing is supported and run from a normal Windows desktop session.
Do not use a headless terminal/SSH session.

## 10. Submission Checklist

Before submitting the assignment:

- Run `build.bat`.
- Confirm it prints `Build successful`.
- Run `run.bat`.
- Enter a name and roll number.
- Answer at least one MCQ and one short answer.
- Submit and confirm the score view.
- Confirm that `data/results.dat` and `data/index.dat` are created.
- Keep source files and `questions.txt`.
- Use [NOTES.md](NOTES.md) to prepare viva explanations.
