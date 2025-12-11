using Microsoft.AspNetCore.Mvc;
using EList.Data;
using EList.Models;

namespace EList.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ListController : ControllerBase
    {
        private readonly ListRepository _repository;
        public ListController(ListRepository repository)
        {
            _repository = repository;


        }
        //get Api/tasks

        //getactivetask
        [HttpGet("active")]
        public IActionResult GetActivetask()
        {
            var tasks = _repository.getActivetask();
            return Ok(tasks);

        }

        // getinactivetask
        [HttpGet("inactive")]
        public IActionResult getInactivetask()
        {
            var tasks = _repository.getInactivetask();
            return Ok(tasks);
        }

        //gettaskbyid
        [HttpGet("GetByid/{task_id}")]
        public IActionResult getTaskbyId(Guid task_id)
        {
            var list = _repository.getTaskbyId(task_id);
            return Ok(list);
        }

        //gettaskbyname
        [HttpGet("GetByname")]
        public IActionResult getTaskbyName([FromQuery] string task_name)
        {
            var list = _repository.getTaskbyName(task_name);
            return Ok(list);
        }

        //insert new task/update
        [HttpPost("SaveTask")]
        public IActionResult insertNewTask([FromBody] Tasks tasks)
        {
            if (tasks == null) return BadRequest();

            var type = "update";

            if (tasks.task_id.ToString() == "00000000-0000-0000-0000-000000000000")
                type = "insert";

            if (_repository.insertNewTask(tasks))
                return Ok(new { success = true, message = (type == "insert") ? "Task Successfully Added." : "Task Successfully Updated" });
            else
                return Ok(new { success = false, message = (type == "insert") ? "An error occured while adding a new task. Please Try Again." : "An error occured while updating the task. Please Try Again." });

        }

        //deletetask
        [HttpDelete("delete/{task_id}")]

        public IActionResult deleteActiveTasks(Guid task_id)
        {


            if (_repository.deleteActiveTasks(task_id))
                return Ok(new { success = true, message = "Task Deleted Successfully" });
            else
                return Ok(new { success = false, message = "An Error Occured When Deleting Task" });


        }

    }
}
