namespace EList.Models
{
    public class Tasks
{
    public Guid task_id { get; set; }
    public string task_name { get; set; } = string.Empty;
    public string task_description { get; set; } = string.Empty;



    public DateTime due_date { get; set; }

    public bool task_isactive { get; set; }

}
}
