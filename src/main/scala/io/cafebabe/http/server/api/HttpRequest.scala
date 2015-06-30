/*
 * Copyright (C) 2015  Vladimir Konstantinov, Yuriy Gintsyak
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.cafebabe.http.server.api

/**
 * HTTP request representation. It contains:
 * <ul>
 *   <li>method
 *   <li>path without prefix
 *   <li>query parameters from uri or body (if Content-Type is application/x-www-form-urlencoded)
 *   <li>content (body)
 *   <li>headers
 *
 * @author Vladimir Konstantinov
 */
case class HttpRequest(
  method: HttpMethod,
  path: String,
  parameters: QueryParams,
  content: HttpContent,
  headers: HttpHeaders
)
