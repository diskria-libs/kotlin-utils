@file:Suppress("NonAsciiCharacters")

package io.github.diskria.kotlin.utils.extensions.common

import com.squareup.kotlinpoet.KModifier
import io.github.diskria.kotlin.utils.words.*
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

typealias KotlinClass<T> = KClass<T>
typealias KotlinProperty<T> = KProperty<T>
typealias KotlinSerializer<T> = KSerializer<T>
typealias KotlinModifier = KModifier

typealias camelCase = CamelCase
typealias `COBOL-CASE` = CobolCase
typealias `dot․case` = DotCase
typealias flatcase = FlatCase
typealias `kebab-case` = KebabCase
// PascalCase is already self-descriptive, no alias needed
typealias `path∕case` = PathCase
typealias SCREAMING_SNAKE_CASE = ScreamingSnakeCase
typealias `Sentence case` = SentenceCase
typealias snake_case = SnakeCase
typealias `space case` = SpaceCase
typealias `Title Case` = TitleCase
typealias `Train-Case` = TrainCase
