package services;

import beans.Question;
import controller.QuestionController;
import rest.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Daniel Klock
 * @version 2.3.1
 *
 * REST service class that defines operations available for the Question resource.
 * This class defines those operations based on the CRUD standard.
 */
@Path("/quizzes/{quizId}/questions/")
public class QuestionService {
    /**
     * List all questions for a given quiz.
     *
     * @param id - The ID of the quiz to get the questions from.
     * @return A {@code List} that contains all the {@code Question} objects,
     * within a given quiz. This list is converted to JSON by Jax-RS.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getQuestions(@PathParam("quizId") int id) {
        return QuestionController.getQuestions(id);
    }

    /**
     * Get a question for a quiz given the id of the question.
     *
     * @param id - The ID of the question.
     * @return A {@code Question} object or {@code null} if not found.
     */
    @GET
    @Path("/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestion(@PathParam("questionId") int id) {
        return QuestionController.getQuestion(id);
    }

    /**
     * Create a new question.
     *
     * @param id        - The ID of the quiz to create a question for.
     * @param question  - The question to create.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createQuestion(@PathParam("quizId") int id, Question question) {
        QuestionController.createQuestion(id, question);
    }

    /**
     * Partially update a question.
     * @param id        - The ID of the question to update.
     * @param question  - Question bean to store the new data.
     */
    @PATCH
    @Path("/{questionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void partiallyUpdateQuestion(@PathParam("questionId") int id, Question question) {
        QuestionController.updateQuestion(id, question, true);
    }

    /**
     * Update a question.
     * @param id        - The ID of the question to update.
     * @param question  - Question bean to store the new data.
     */
    @PUT
    @Path("/{questionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateQuestion(@PathParam("questionId") int id, Question question) {
        QuestionController.updateQuestion(id, question, false);
    }

    /**
     * Delete a question.
     *
     * @param quizId        - The ID of the quiz the question is apart of.
     * @param questionId    - The ID of the question to delete.
     */
    @DELETE
    @Path("/{questionId}")
    public void deleteQuestion(@PathParam("quizId") int quizId, @PathParam("questionId") int questionId) {
        QuestionController.deleteQuestion(quizId, questionId);
    }
}
