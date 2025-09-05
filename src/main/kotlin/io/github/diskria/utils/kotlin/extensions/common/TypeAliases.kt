@file:Suppress("NonAsciiCharacters")

package io.github.diskria.utils.kotlin.extensions.common

import com.squareup.kotlinpoet.KModifier
import io.github.diskria.utils.kotlin.words.CamelCase
import io.github.diskria.utils.kotlin.words.CobolCase
import io.github.diskria.utils.kotlin.words.DotCase
import io.github.diskria.utils.kotlin.words.FlatCase
import io.github.diskria.utils.kotlin.words.KebabCase
import io.github.diskria.utils.kotlin.words.PathCase
import io.github.diskria.utils.kotlin.words.ScreamingSnakeCase
import io.github.diskria.utils.kotlin.words.SentenceCase
import io.github.diskria.utils.kotlin.words.SnakeCase
import io.github.diskria.utils.kotlin.words.SpaceCase
import io.github.diskria.utils.kotlin.words.TitleCase
import io.github.diskria.utils.kotlin.words.TrainCase
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
