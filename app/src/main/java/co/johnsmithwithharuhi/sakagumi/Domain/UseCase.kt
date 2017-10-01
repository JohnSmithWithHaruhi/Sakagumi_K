package co.johnsmithwithharuhi.sakagumi.Domain

interface UseCase<out T> {

  fun execute(): T

}