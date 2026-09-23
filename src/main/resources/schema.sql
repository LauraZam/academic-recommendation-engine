DROP TABLE IF EXISTS recommendation_logs CASCADE;
DROP TABLE IF EXISTS student_competencies CASCADE;
DROP TABLE IF EXISTS enrollments CASCADE;
DROP TABLE IF EXISTS competencies CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS students CASCADE;

-- 1. Students Table
CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          student_number VARCHAR(20) UNIQUE NOT NULL,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          academic_year INT NOT NULL,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Courses Table
CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         course_code VARCHAR(15) UNIQUE NOT NULL,
                         title VARCHAR(150) NOT NULL,
                         description TEXT,
                         credits INT NOT NULL,
                         created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. Competencies
CREATE TABLE competencies (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(100) UNIQUE NOT NULL,
                              category VARCHAR(50) NOT NULL,
                              description TEXT
);

-- 4. Enrollments (Bridge Table: Students <-> Courses)
CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                             course_id BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                             grade NUMERIC(3,2),
                             semester VARCHAR(20) NOT NULL,
                             CONSTRAINT unique_student_course UNIQUE(student_id, course_id)
);

-- 5. Student Competency Map
CREATE TABLE student_competencies (
                                      id BIGSERIAL PRIMARY KEY,
                                      student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                                      competency_id BIGINT NOT NULL REFERENCES competencies(id) ON DELETE CASCADE,
                                      score NUMERIC(5,4) NOT NULL,
                                      last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT unique_student_competency UNIQUE(student_id, competency_id)
);

-- 6. Recommendation Logs
CREATE TABLE recommendation_logs (
                                     id BIGSERIAL PRIMARY KEY,
                                     student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                                     recommended_course_id BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                     confidence_score NUMERIC(5,4) NOT NULL,
                                     reason VARCHAR(255),
                                     generated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_students_student_number ON students(student_number);
CREATE INDEX idx_enrollments_student_id ON enrollments(student_id);
CREATE INDEX idx_student_competencies_student_id ON student_competencies(student_id);
CREATE INDEX idx_recommendations_student_id ON recommendation_logs(student_id);