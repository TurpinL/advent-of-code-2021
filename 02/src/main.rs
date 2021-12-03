use std::fs;
use itertools::Itertools;
use regex::Regex;

fn main() {
    println!("Part 1");
    part1();

    println!("\nPart 2");
    part2();
}

fn part1() {
    let input_lines = include_str!("../input").lines();
    let re = Regex::new(r#"^(\w+) (\d+)$"#).unwrap();

    let mut depth = 0;
    let mut horizontal_pos = 0;

    for line in input_lines {
        let caps = re.captures(line).unwrap();
        let direction = &caps[1];
        let value = &caps[2].parse::<i32>().unwrap();

        match direction {
            "forward" => { horizontal_pos += value; },
            "up" => { depth -= value; },
            "down" => { depth += value; },
            _ => {}
        }
    }

    println!("{} x {} = {}", depth, horizontal_pos, depth * horizontal_pos )
}

fn part2() {
    let input_lines = include_str!("../input").lines();
    let re = Regex::new(r#"^(\w+) (\d+)$"#).unwrap();

    let mut aim = 0;
    let mut depth = 0;
    let mut horizontal_pos = 0;

    for line in input_lines {
        let caps = re.captures(line).unwrap();
        let direction = &caps[1];
        let value = &caps[2].parse::<i32>().unwrap();

        match direction {
            "forward" => {
                horizontal_pos += value;
                depth += value * aim;
            },
            "up" => { aim -= value; },
            "down" => { aim += value; },
            _ => {}
        }
    }

    println!("{} x {} = {}", depth, horizontal_pos, depth * horizontal_pos )
}