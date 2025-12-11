using System.Collections.Generic;
using System.Data;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;
using EList.Models;
using System.Security.Cryptography.Pkcs;
using Microsoft.AspNetCore.Identity;

namespace EList.Data
{
    public class ListRepository
    {
        private readonly string _connectionString;
        public ListRepository(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("DefaultConnection");

        }

        public List<Tasks> getActivetask()
        {
            var listofactivetask = new List<Tasks>();
            var sQuery = "getActivetask";
            using (var sqlconn = new SqlConnection(_connectionString))
            using (var sqlcmd = new SqlCommand(sQuery, sqlconn))
            {
                sqlcmd.CommandType = CommandType.StoredProcedure;
                sqlconn.Open();
                using (var reader = sqlcmd.ExecuteReader())
                {

                    while (reader.Read())
                    {
                        listofactivetask.Add(new Tasks
                        {
                            task_id = reader.GetGuid("task_id"),
                            task_name = reader.GetString("task_name"),
                            task_description = reader.GetString("task_description"),
                            task_isactive = reader.GetBoolean(reader.GetOrdinal("ISACTIVE")),

                        });

                    }

                }

            }
            return listofactivetask;

        }

        public List<Tasks> getInactivetask()
        {
            var listofinactivetask = new List<Tasks>();
            var sQuery = "dbo.getInactivetask";
            using (var sqlconn = new SqlConnection(_connectionString))
            using (var sqlcmd = new SqlCommand(sQuery, sqlconn))
            {
                sqlcmd.CommandType = CommandType.StoredProcedure;
                sqlconn.Open();
                using (var reader = sqlcmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        listofinactivetask.Add(new Tasks
                        {
                            task_id = reader.GetGuid("task_id"),
                            task_name = reader.GetString("task_name"),
                            task_description = reader.GetString("task_description"),
                            task_isactive = reader.GetBoolean(reader.GetOrdinal("ISACTIVE")),
                        });
                    }
                }
            }
            return listofinactivetask;

        }

        public Tasks getTaskbyId(Guid task_id)
        {
            Tasks tasks = new Tasks();
            var sQuery = "getTasksbyId";
            using (var sqlconn = new SqlConnection(_connectionString))
            using (var sqlcmd = new SqlCommand(sQuery, sqlconn))
            {
                sqlcmd.CommandType = CommandType.StoredProcedure;
                sqlcmd.Parameters.AddWithValue("@task_id", task_id);
                sqlconn.Open();
                using (var reader = sqlcmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        tasks = new Tasks();
                        tasks.task_id = reader.GetGuid("task_id");
                        tasks.task_name = reader.GetString("task_name");
                        tasks.task_description = reader.GetString("task_description");
                        tasks.due_date = reader.GetDateTime("due_date");
                        tasks.task_isactive = reader.GetBoolean(reader.GetOrdinal("ISACTIVE"));
                    }
                }
            }
            return tasks;
        }

        public Tasks getTaskbyName(string task_name)
        {
            Tasks tasks = new Tasks();
            var sQuery = "getTaskbyName";
            using (var sqlconn = new SqlConnection(_connectionString))
            using (var sqlcmd = new SqlCommand(sQuery, sqlconn))
            {
                sqlcmd.CommandType = CommandType.StoredProcedure;
                sqlcmd.Parameters.AddWithValue("@task_name", task_name);
                sqlconn.Open();
                using (var reader = sqlcmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        tasks.task_id = reader.GetGuid("task_id");
                        tasks.task_name = reader.GetString("task_name");
                        tasks.task_description = reader.GetString("task_description");
                        tasks.due_date = reader.GetDateTime("due_date");
                        tasks.task_isactive = reader.GetBoolean(reader.GetOrdinal("ISACTIVE"));
                    }
                }
            }
            return tasks;
        }

        public bool insertNewTask(Tasks tasks)
        {
            bool isSuccess = false;
            try
            {
                var sQuery = "dbo.insertNewTask";
                using (var sqlconn = new SqlConnection(_connectionString))
                using (var sqlcmd = new SqlCommand(sQuery, sqlconn))
                {
                    sqlcmd.CommandType = CommandType.StoredProcedure;
                    sqlcmd.Parameters.AddWithValue("@task_id", tasks.task_id);
                    sqlcmd.Parameters.AddWithValue("@task_name", tasks.task_name);
                    sqlcmd.Parameters.AddWithValue("@task_description", tasks.task_description);
                    sqlcmd.Parameters.AddWithValue("@due_date", tasks.due_date);
                    sqlcmd.Parameters.AddWithValue("@task_isactive", tasks.task_isactive);

                    sqlconn.Open();
                    sqlcmd.ExecuteNonQuery();
                    isSuccess = true;

                }

            }
            catch (Exception e)
            {
                isSuccess = false;

            }
            return isSuccess;

        }

        public bool deleteActiveTasks(Guid task_id)
        {
            bool isSuccess = false;
            try
            {
                using (var sqlconn = new SqlConnection(_connectionString))
                using (var sqlcmd = new SqlCommand("dbo.deleteActiveTasks", sqlconn))
                {
                    sqlcmd.CommandType = CommandType.StoredProcedure;
                    sqlcmd.Parameters.AddWithValue("@task_id", task_id);

                    sqlconn.Open();
                    sqlcmd.ExecuteNonQuery();
                    isSuccess = true;
                }

            }
            catch (Exception e)
            {
                isSuccess = false;
            }
            return isSuccess;
        }
    }
}

