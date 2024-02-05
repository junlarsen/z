package codes.jun.configuration

import codes.jun.once.SecretNotFoundException
import codes.jun.todo.TodoItemNotFoundException
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
}
