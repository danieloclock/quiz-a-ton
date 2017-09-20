package controller;

import beans.Question;
import beans.Quiz;
import dao.QuestionDAO;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionController {
    private static final QuizController quizController = new QuizController();
    private static final QuestionDAO questionDAO = new QuestionDAO(); // TODO: Implement a database solution.
    private static final Map<Integer, Question> questions = new HashMap<>();

    /**
     * Get all questions.
     *
     * @return A {@code List} of all questions.
     */
    public List<Question> getQuestions() {
        // TODO: Check database for new or newly modified questions.

        List<Question> list = new ArrayList<>();
        list.addAll(questions.values());
        return list;
    }

    /**
     * Get all questions within a quiz.
     *
     * @param quizId - The ID of the quiz to get questions from.
     * @throws NotFoundException - If the quiz with the given ID is not found.
     * @return A {@code List} of questions.
     */
    public List<Question> getQuestions(int quizId) {
        Quiz quiz = quizController.getQuiz(quizId);
        if (quiz == null) {
            throw new NotFoundException("Could not find a quiz with the given ID of " + quizId + ".");
        }

        /*
         * Creates a list of all the questions in a quiz, based on the question ID
         * list in the quiz object.
         */
        List<Question> list = new ArrayList<>();
        for (Integer questionId : quiz.getQuestions()) {
            Question question = getQuestion(questionId);
            if (question != null) {
                list.add(question);
            }
        }
        return list;
    }

    /**
     * Get a question given an ID.
     *
     * @param id - The ID of the question.
     * @return A {@code Question}.
     * @throws NotFoundException - If a question with the given ID is not found.
     */
    public Question getQuestion(int id) {
        // TODO: Check database for new or newly modified quizzes.

        Question question = questions.get(id);
        if (question == null) {
            throw new NotFoundException("Could not find a question with the given ID of " + id + ".");
        }
        return question;
    }

    /**
     * Create a new question.
     *
     * @param question - The question to create.
     */
    public void createQuestion(Question question) {
        if (question == null) {
            throw new NullPointerException("Question object can not be null.");
        }

        // TODO: Update database with new question.
        questions.putIfAbsent(question.getId(), question);
    }

    /**
     * Create a new question linked to an existing quiz.
     *
     * @param id        - The ID of the quiz to find.
     * @param question  - The question to add to the quiz.
     * @throws NullPointerException - If the {@code Question} object is {@code null}.
     * @throws NotFoundException    - If a quiz with the given ID is not found.
     */
    public void createQuestion(int id, Question question) {
        if (question == null) {
            throw new NullPointerException("Question object can not be null.");
        }

        // Check that quiz with given ID exists.
        Quiz quiz = quizController.getQuiz(id);
        if (quiz == null) {
            throw new NotFoundException("Can not find a quiz with id of " + id + ".");
        }

        // TODO: Update database with new question.

        // Add question ID to quiz.
        List<Integer> tempQuestions = quiz.getQuestions();
        int questionId = question.getId();
        tempQuestions.add(questionId);

        // Update memory and database with new information.
        quizController.updateQuiz(quiz.getId(), quiz);
        questions.putIfAbsent(questionId, question);
    }

    /**
     * Update a question.
     *
     * @param id        - The ID of the question to update.
     * @param question  - Question bean to store the new data.
     * @throws NullPointerException - If the {@code Question} object is {@code null}.
     * @throws NotFoundException    - If a question with the given ID is not found.
     */
    public void updateQuestion(int id, Question question) {
        if (question == null) {
            throw new NullPointerException("Question object can not be null.");
        }

        Question found = questions.get(id);
        if (found == null) {
            throw new NotFoundException("Can not find a question with id of " + id + ".");
        }

        // Get new data.
        String newQuestion = question.getQuestion();
        String imageURL = question.getImageURL();
        List<String> answers = question.getAnswers();
        int points = question.getPoints();
        int correctAnswerIndex = question.getCorrectAnswerIndex();
        int duration = question.getDuration();

        // Update with new information if available.
        found.setQuestion((newQuestion != null) ? newQuestion : found.getQuestion());
        found.setImageURL((imageURL != null) ? imageURL : found.getImageURL());
        found.setAnswers((answers != null) ? answers : found.getAnswers());
        found.setPoints((points > -1) ? points : found.getPoints());
        found.setCorrectAnswerIndex((correctAnswerIndex > -1) ? correctAnswerIndex : found.getCorrectAnswerIndex());
        found.setDuration((duration > 10) ? duration : found.getDuration());

        // TODO: Update database with altered object.
        questions.put(id, found);
    }

    /**
     * Delete a question given a ID.
     *
     * @param id - The ID of the question to delete.
     */
    public void deleteQuestion(int id) {
        // TODO: Remove question from database.
        questions.remove(id);
    }
}
