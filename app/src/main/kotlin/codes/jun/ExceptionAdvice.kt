package codes.jun

import codes.jun.once.SecretNotFoundException
import codes.jun.todo.TodoListAlreadyExistsException
import codes.jun.todo.TodoListNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdvice {
  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Void> {
    return ResponseEntity(HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler(TodoListAlreadyExistsException::class)
  fun handleTodoListAlreadyExistsException(ex: TodoListAlreadyExistsException): ResponseEntity<Void> {
    return ResponseEntity(HttpStatus.CONFLICT)
  }

  @ExceptionHandler(TodoListNotFoundException::class)
  fun handleTodoListNotFoundException(ex: TodoListNotFoundException): ResponseEntity<Void> {
    return ResponseEntity(HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler(SecretNotFoundException::class)
  fun handleSecretNotFoundException(ex: SecretNotFoundException): ResponseEntity<Void> {
    return ResponseEntity(HttpStatus.NOT_FOUND)
  }
}
